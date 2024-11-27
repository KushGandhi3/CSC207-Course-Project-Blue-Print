package interface_adapter.display_checker;

import use_case.display_checker.DisplayCheckerOutputBoundary;
import interface_adapter.ViewManagerModel;

/**
 * Presenter for the checker use case.
 */
public class DisplayCheckerPresenter implements DisplayCheckerOutputBoundary {

    private final DisplayCheckerViewModel displayCheckerViewModel;
    private final ViewManagerModel viewManagerModel;

    public DisplayCheckerPresenter(DisplayCheckerViewModel displayCheckerViewModel, ViewManagerModel viewManagerModel) {
        this.displayCheckerViewModel = displayCheckerViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Prepare the view when the condition is met for the checker related Use Case
     */
    @Override
    public void prepareCondMetView() {

        DisplayCheckerState displayCheckerState = displayCheckerViewModel.getState();
        displayCheckerState.setMessage("exist");
        displayCheckerViewModel.setState(displayCheckerState);

        displayCheckerViewModel.firePropertyChanged();
    }

    /**
     * Prepare the view when the condition is not met for the checker related Use Case
     */
    @Override
    public void prepareCondNotMetView() {

        DisplayCheckerState displayCheckerState = displayCheckerViewModel.getState();
        displayCheckerState.setMessage("nonexist");
        displayCheckerViewModel.setState(displayCheckerState);

        displayCheckerViewModel.firePropertyChanged();
    }

    /**
     * Prepare the view to switch to the home view
     */
    @Override
    public void prepareHomeView() {
        // Reset the state of the checker view to default state
        DisplayCheckerState displayCheckerState = displayCheckerViewModel.getState();
        displayCheckerViewModel.setState(displayCheckerState);

        // switch and update to home view
        viewManagerModel.setState("home");
        displayCheckerViewModel.firePropertyChanged();
    }

    /**
     * Prepare the view when the location is empty
     */
    @Override
    public void prepareLocationEmptyView() {
        DisplayCheckerState displayCheckerState = displayCheckerViewModel.getState();
        displayCheckerState.setMessage("empty");
        displayCheckerViewModel.firePropertyChanged();
    }

    /**
     * Prepare the view when the location is invalid
     */
    @Override
    public void prepareInvalidLocationView() {
        DisplayCheckerState displayCheckerState = displayCheckerViewModel.getState();
        displayCheckerState.setMessage("invalid");
        displayCheckerViewModel.firePropertyChanged();
    }
}
