package ru.softwerke.shop.service;

import org.eclipse.jetty.util.StringUtil;
import ru.softwerke.shop.Utils.ServiceUtils;
import ru.softwerke.shop.controller.RequestException;
import ru.softwerke.shop.model.Item;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Data service for items
 */
public class DataService<T extends Item> {
    private static final String PAGE = "page";
    private static final String COUNT = "count";
    private static final String ORDER_BY = "orderBy";
    static final String BY_ID = "id";

    private static final long DEFAULT_COUNT = 10;
    private static final long MAX_COUNT = 1000;


    CopyOnWriteArrayList<T> items;
    private long page = 0;
    private long count = DEFAULT_COUNT;

    Map<String, CheckedFunction<String, Predicate<T>>> predicates;
    Map<String, Comparator<T>> comparators;

    public T addItem(T item) {
        items.add(item);
        return item;
    }

    public T getItemById(long id) {
        return items.stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }

    public List<T> getItemsList() {
        return items;
    }

    /**
     * Returns list of items matching filters from query params
     * Predicates and comparators defined in implementing class
     * Result devides on pages
     *
     * @param queryParams
     * @return List<Item>
     * @throws RequestException
     */
    public  List<T> getList(MultivaluedMap<String, String> queryParams) throws RequestException {
        count = parseCount(queryParams);
        page = parsePage(queryParams);

        List<Comparator<T>> sorts = getComparators(queryParams);

        Stream<T> stream = items.stream();

        for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
            String param = entry.getKey();

            CheckedFunction<String, Predicate<T>> predicate = predicates.get(param);

            if (param.equals(PAGE) || param.equals(COUNT) || param.equals(ORDER_BY)) {
                continue;
            } else {
                if (predicate == null) {
                    throw new RequestException("Illegal parameter name: " + param);
                }
            }

            stream = stream.filter(predicate.apply(entry.getValue().get(0)));
        }

        for (Comparator<T> comparator : sorts) {
            stream = stream.sorted(comparator);
        }
        return stream.skip(page * count).limit(count).collect(Collectors.toList());
    }

    private List<Comparator<T>> getComparators(MultivaluedMap<String, String> queryParams) throws RequestException {
        List<String> sorts = queryParams.get(ORDER_BY);
        List<Comparator<T>> result = new ArrayList<>();

        if (sorts == null) {
            result.add(comparators.get(BY_ID));
            return result;
        }

        for (String orderBy : sorts) {
            if (StringUtil.isNotBlank(orderBy)) {
                boolean reversed = false;
                if (orderBy.charAt(0) == '-') {
                    orderBy = orderBy.substring(1);
                    reversed = true;
                }

                Comparator<T> comparator = comparators.get(orderBy);
                if (comparator == null) {
                    throw new RequestException("Illegal parameter value.\norderBy: " + orderBy);
                }

                result.add(reversed ? comparator.reversed() : comparator);
            }
        }
        return result;
    }

    private long parseCount(MultivaluedMap<String, String> queryParams) throws RequestException {
        String countStr = queryParams.getFirst(COUNT);

        if (StringUtil.isNotBlank(countStr)) {
            long count = ServiceUtils.parseNumber(countStr, Long::parseLong);
            if (count < 0) {
                throw new RequestException("Positive number expected, instead: " + countStr);
            }

            if (count > MAX_COUNT) {
                throw new RequestException("Max count is " + MAX_COUNT);
            }

            return count;
        }
        return this.count;
    }

    private long parsePage(MultivaluedMap<String, String> queryParams) throws RequestException {
        String pageStr = queryParams.getFirst(PAGE);

        if (StringUtil.isNotBlank(pageStr)) {
            long page = ServiceUtils.parseNumber(pageStr, Long::parseLong) - 1;
            if (page < 0) {
                throw new RequestException("Positive number expected, instead: " + pageStr);
            }
            return page;
        }
        return this.page;
    }

    public CopyOnWriteArrayList<T> getItems() {
        return items;
    }

    interface CheckedFunction<T, R> {
        R apply(T t) throws RequestException;
    }

}
