package usantatecla.draughts.views;

import usantatecla.draughts.controllers.InteractorController;
import usantatecla.draughts.controllers.InteractorControllersVisitor;
import usantatecla.draughts.controllers.PlayController;
import usantatecla.draughts.controllers.ResumeController;
import usantatecla.draughts.controllers.StartController;
import usantatecla.draughts.utils.YesNoDialog;

public class View implements InteractorControllersVisitor {

    private StartView startView;
    private PlayView playView;

    private static final String MESSAGE = "¿Queréis jugar otra";
    private YesNoDialog yesNoDialog;

    public View(){
        this.startView = new StartView();
        this.playView = new PlayView();

        this.yesNoDialog = new YesNoDialog();
    }

    public void interact(InteractorController controller) {
        assert controller != null;
        controller.accept(this);
    }

    @Override
    public void visit(StartController startController) {
        assert startController != null;
        this.startView.interact(startController);
    }

    @Override
    public void visit(PlayController playController) {
        assert playController != null;
        this.playView.interact(playController);
    }

    @Override
    public void visit(ResumeController resumeController) {
        assert resumeController != null;
        this.interact(resumeController);
    }

    void interact(ResumeController resumeController) {
        assert resumeController != null;
        if (this.yesNoDialog.read(MESSAGE))
            resumeController.reset();
        else
            resumeController.next();
    }

}
