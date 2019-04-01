package ru.softwerke.shop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.internal.NotNull;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * Class <code>Client</code>  contains information about shop client
 *
 * @authorIlfat
 */

public class Client {
    /**
     * Current using ID
     */
    static long currentID = 0;

    public static final String ID_FILED = "id";
    public static final String SECONDNAME_FIELD = "second name";
    public static final String NAME_FIELD = "name";
    public static final String PATRONYMIC_FIELD = "patronymic";
    public static final String BIRTHDAY_FIELD = "birthday";

    @JsonProperty(ID_FILED)
    long ID;

    @JsonProperty(NAME_FIELD)
    String name;

    @JsonProperty(SECONDNAME_FIELD)
    String secondName;

    @JsonProperty(PATRONYMIC_FIELD)
    String patronymic;

    /**
     * Date format (dd.MM.yyyy)
     */
    @JsonProperty(BIRTHDAY_FIELD)
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    LocalDate birthday;

    @JsonCreator
    public Client (@NotNull @JsonProperty(SECONDNAME_FIELD) String secondName,
                  @NotNull  @JsonProperty(NAME_FIELD) String name,
                  @NotNull  @JsonProperty(PATRONYMIC_FIELD) String patronymic,
                  @NotNull @JsonProperty(BIRTHDAY_FIELD) LocalDate birthday) {
        ID = ++currentID;
        this.name = name;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.birthday = birthday;
    }

    public long getID() {
        return ID;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + ID +
                ", secondName='" + secondName + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
