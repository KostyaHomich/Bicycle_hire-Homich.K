package epam.project.command.page;

import epam.project.command.Command;
import epam.project.command.PageConst;
import epam.project.dto.ResponseContent;
import epam.project.util.ResponseContentBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CommandShowRegistrationPage implements Command {

    private static final Logger LOGGER = LogManager.getLogger(CommandShowRegistrationPage.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        return  ResponseContentBuilder.buildForwardResponseContent(PageConst.REGISTRATION_PAGE_PATH);
    }
}
