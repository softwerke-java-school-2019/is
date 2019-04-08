package ru.softwerke.shop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class <code>Client</code>  contains information about shop client
 *
 * @authorIlfat
 */

public class Client extends Item {

    public static final String SECONDNAME_FIELD = "second name";
    public static final String NAME_FIELD = "name";
    public static final String PATRONYMIC_FIELD = "patronymic";
    public static final String BIRTHDAY_FIELD = "birthday";

    @JsonProperty(NAME_FIELD)
    private String name;

    @JsonProperty(SECONDNAME_FIELD)
    private String secondName;

    @JsonProperty(PATRONYMIC_FIELD)
    private String patronymic;

    /**
     * Date format (dd.MM.yyyy)
     */
    @JsonProperty(BIRTHDAY_FIELD)
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate birthday;

    @JsonCreator
    public Client (@JsonProperty(SECONDNAME_FIELD) String secondName,
                   @JsonProperty(NAME_FIELD) String name,
                   @JsonProperty(PATRONYMIC_FIELD) String patronymic,
                   @JsonProperty(BIRTHDAY_FIELD) LocalDate birthday) {
        super();
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
