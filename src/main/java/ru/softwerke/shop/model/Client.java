package ru.softwerke.shop.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import ru.softwerke.shop.Utils.ClientDeserializer;
import ru.softwerke.shop.Utils.ClientSerializer;

/**
 * Class <code>Client</code>  contains information about shop client
 *
 * @authorIlfat
 */
@JsonSerialize(using = ClientSerializer.class)
@JsonDeserialize(using = ClientDeserializer.class)
public class Client extends Item {
    private static AtomicLong currentID = new AtomicLong(0);

    public static final String SECONDNAME_FIELD = "secondName";
    public static final String NAME_FIELD = "name";
    public static final String PATRONYMIC_FIELD = "patronymic";
    public static final String BIRTHDAY_FIELD = "birthday";

    private String name;

    private String secondName;

    private String patronymic;

    /**
     * Date format (dd.MM.yyyy)
     */
    private LocalDate birthday;

    public Client (String secondName,
                   String name,
                   String patronymic,
                   LocalDate birthday) {
        this.id = currentID.getAndIncrement();
        this.name = name;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.birthday = birthday;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Client other = (Client) o;
        return id == other.id &&
                name.equals(other.name) &&
                secondName.equals(other.secondName) &&
                patronymic.equals(other.patronymic) &&
                birthday.equals(other.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, secondName, patronymic, birthday);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", secondName='" + secondName + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
