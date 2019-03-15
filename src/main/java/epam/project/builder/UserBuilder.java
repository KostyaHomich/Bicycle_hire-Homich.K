package epam.project.builder;

import epam.project.command.CommandRegisterUser;
import epam.project.entity.User;
import epam.project.service.HashGenerator;
import epam.project.service.ServiceFactory;
import epam.project.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Map;


public class UserBuilder implements Builder<User> {

    private static final String ID = "id";
    private static final String PASSWORD = "password";
    private static final String STATUS = "status";
    private static final String LOGIN = "login";
    private static final String EMAIL = "email";
    private static final String FIST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String BALANCE = "balance";
    private static final Logger LOGGER = LogManager.getLogger(CommandRegisterUser.class);


    @Override
    public User build(Map<String, String> params) throws ServiceException {

        HashGenerator hashGenerator = ServiceFactory.getInstance().getHashGenerator();
        User user = new User();
        for (Object key : params.keySet()) {
            String keyStr = (String) key;
            String value = params.get(keyStr);
            switch (keyStr) {
                case LOGIN:
                    user.setLogin(value);
                    break;
                case PASSWORD:
                    user.setPassword(hashGenerator.encode(value + user.getLogin()));
                    break;
                case FIST_NAME:
                    user.setFirstName(value);
                    break;
                case LAST_NAME:
                    user.setLastName(value);
                    break;
                case EMAIL:
                    user.setEmail(value);
                    break;
                case STATUS:
                    user.setStatus(value);
                    break;
                case ID:
                    user.setId(Integer.valueOf(value));
                case BALANCE:
                    user.setBalance(BigDecimal.valueOf(Double.valueOf(value)));
                default:
                    break;
            }

        }

        return user;

    }

}