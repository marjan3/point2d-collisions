package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.canvas.point.DrawablePoint;
import com.mtanevski.math2d.gui.dialogs.SimpleDialog;
import javafx.application.Platform;
import lombok.ToString;

@ToString
public class CreatePointCommand implements Command {
    private DrawablePoint drawablePoint;
    private CreateRequest createRequest;

    public CreatePointCommand(CreateRequest createRequest) {
        this.createRequest = createRequest;
    }

    @Override
    public void execute() {
        Platform.runLater(() -> {
            Overlay.deselectAll();
            if (drawablePoint == null) {
                if (createRequest == null) {
                    var result = SimpleDialog.showXYDialog(Constants.Labels.NEW_POINT_LABEL);
                    createRequest = CreateRequest.fromDialogResult(result);
                    drawablePoint = new DrawablePoint(createRequest.getName(), createRequest.getX(), createRequest.getY());
                } else if (createRequest.getName() == null) {
                    var result =
                            SimpleDialog.showXYDialog(Constants.Labels.NEW_POINT_LABEL, null, createRequest.getX(),
                                    createRequest.getY());
                    createRequest = CreateRequest.fromDialogResult(result);
                    drawablePoint = new DrawablePoint(createRequest.getName(), createRequest.getX(), createRequest.getY());
                } else {
                    drawablePoint = new DrawablePoint(createRequest.getName(), createRequest.getX(), createRequest.getY());
                }
            }
            Overlay.drawPoint(drawablePoint);
        });
    }

    @Override
    public void undo() {
        Platform.runLater(() -> {
            Overlay.deselectAll();
            Overlay.remove(drawablePoint);
        });
    }

}
