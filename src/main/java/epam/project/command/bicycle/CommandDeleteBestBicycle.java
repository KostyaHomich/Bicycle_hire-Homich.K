package epam.project.command.bicycle;

import epam.project.command.Command;
import epam.project.command.CommandException;
import epam.project.command.CommandType;
import epam.project.dto.ResponseContent;
import epam.project.service.exception.ServiceException;
import epam.project.service.impl.BicycleService;
import epam.project.util.ResponseContentBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CommandDeleteBestBicycle implements Command {

    private static final Logger LOGGER = LogManager.getLogger(CommandDeleteBestBicycle.class);

    @Override
    public ResponseContent execute(HttpServletRequest request) throws CommandException {
        try {

            BicycleService bicycleService = new BicycleService();

            int id = Integer.parseInt(request.getParameter("bicycleId"));
            bicycleService.deleteBestBicycle(id);

            return ResponseContentBuilder.buildCommandResponseContent(CommandType.SHOW_BEST_BICYCLE_LIST, request);
        } catch (ServiceException e) {
            LOGGER.error("Failed to delete bicycle", e);
            request.setAttribute("error", "bicycle.error.delete_bicycle");
            throw new CommandException("Failed to delete bicycle");
        }

    }
}