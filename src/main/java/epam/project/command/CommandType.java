package epam.project.command;

import epam.project.dto.Restrictions;
import epam.project.entity.UserRole;

import java.util.Optional;
import java.util.stream.Stream;

public enum CommandType {


    ADD_BICYCLE(new Restrictions().setRoles(UserRole.ADMIN.name())),
    ADD_BEST_BICYCLE(new Restrictions().setRoles(UserRole.USER.name())),
    DELETE_BICYCLE(new Restrictions().setRoles(UserRole.ADMIN.name())),
    DELETE_BEST_BICYCLE(new Restrictions().setRoles(UserRole.USER.name())),
    UPDATE_BICYCLE(new Restrictions().setRoles(UserRole.ADMIN.name())),
    ADD_POINT_HIRE(new Restrictions().setRoles(UserRole.ADMIN.name())),
    DELETE_POINT_HIRE(new Restrictions().setRoles(UserRole.ADMIN.name())),
    UPDATE_POINT_HIRE(new Restrictions().setRoles(UserRole.ADMIN.name())),
    REGISTER_USER,
    UPDATE_USER(new Restrictions().setRoles(UserRole.ADMIN.name())),
    DELETE_USER(new Restrictions().setRoles(UserRole.ADMIN.name())),
    SHOW_MAIN_PAGE,
    SHOW_REGISTRATION_PAGE,
    SHOW_LOGIN_PAGE,
    LOGIN,
    SHOW_USER_LIST(new Restrictions().setRoles(UserRole.ADMIN.name())),
    SHOW_BICYCLE_LIST(new Restrictions().setRoles(UserRole.ADMIN.name(),UserRole.USER.name())),
    SHOW_BEST_BICYCLE_LIST(new Restrictions().setRoles(UserRole.USER.name())),
    SHOW_POINT_HIRE_LIST(new Restrictions().setRoles(UserRole.ADMIN.name(),UserRole.USER.name())),
    LOGOUT(new Restrictions().setRoles(UserRole.ADMIN.name(),UserRole.USER.name())),
    SHOW_USER_DETAILS(new Restrictions().setRoles(UserRole.ADMIN.name())),
    SHOW_BICYCLE_DETAILS(new Restrictions().setRoles(UserRole.ADMIN.name(),UserRole.USER.name())),
    SHOW_POINT_HIRE_DETAILS(new Restrictions().setRoles(UserRole.ADMIN.name(),UserRole.USER.name())),
    SHOW_USER_PAGE(new Restrictions().setRoles(UserRole.ADMIN.name()));

    public static Optional<CommandType> of(String name) {
        return Stream.of(CommandType.values())
                .filter(e -> e.name().equalsIgnoreCase(name))
                .findFirst();
    }
    public Restrictions getRestrictions() {

        return restrictions;
    }

    private Restrictions restrictions;

    CommandType(Restrictions restrictions) {
        this.restrictions = restrictions;
    }

    CommandType() {
        this.restrictions = new Restrictions();
    }

}
