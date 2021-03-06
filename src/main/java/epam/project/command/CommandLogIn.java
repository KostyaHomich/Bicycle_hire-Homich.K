package epam.project.command;

import epam.project.builder.UserBuilder;
import epam.project.dto.ResponseContent;
import epam.project.entity.User;
import epam.project.service.exception.ServiceException;
import epam.project.service.impl.UserService;
import epam.project.util.RequestParameterParser;
import epam.project.util.ResponseContentBuilder;
import epam.project.validation.ValidationResult;
import epam.project.validation.impl.ContainsValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public class CommandLogIn implements Command {
    private static final String USER = "signInUser";
    private static final Logger LOGGER = LogManager.getLogger(CommandLogIn.class);


    @Override
    public ResponseContent execute(HttpServletRequest request) throws CommandException {

        try {
            UserService userService = new UserService();
            UserBuilder userBuilder = new UserBuilder();
            ContainsValidator userValidator = new ContainsValidator();

            Map<String, String> parameters = RequestParameterParser.parseParameters(request);
            ValidationResult validationResult = userValidator.doValidate(parameters);

            if (validationResult.getErrors().size() == 0) {
                User user = userBuilder.build(parameters);
                User signInUser = userService.signIn(user.getLogin(), user.getPassword());

                if (!signInUser.getStatus().equalsIgnoreCase("banned")) {
                    request.getSession().setAttribute(USER, signInUser);
                    return ResponseContentBuilder.buildCommandResponseContent(CommandType.SHOW_USER_PAGE,request);
                } else {
                    request.setAttribute("error","user.error.banned");
                    return  ResponseContentBuilder.buildForwardResponseContent(PageConst.LOGIN_PAGE_PATH);
                }
            } else {
                request.setAttribute("errorsList", validationResult);
                return  ResponseContentBuilder.buildForwardResponseContent(PageConst.LOGIN_PAGE_PATH);
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed to login user", e);
            request.setAttribute("error","page.error.login_failed");
            throw new CommandException("Failed to login user");
        }

    }
}
