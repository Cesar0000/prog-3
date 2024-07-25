package br.upe.controllers;

import br.upe.pojos.*;

public class UserController {
    public UserController(StateController stateController, CRUDController crudController) {
        this.stateController = stateController;
        this.crudController = crudController;
    }

    private final StateController stateController;
    private final CRUDController crudController;

    public void updateUserName(String userName){
        User source;
        if(stateController.getCurrentUser() instanceof AdminUser){
            source = KeeperInterface.createAdminUser();
        } else {
            source = KeeperInterface.createCommomUser();
        }
        source.setName(userName);
        crudController.userCRUD.updateUser(stateController.getCurrentUser().getUuid(), source);
    }
    public void updateUserEmail(String userEmail){
        User source;
        if(stateController.getCurrentUser() instanceof AdminUser){
            source = KeeperInterface.createAdminUser();
        } else {
            source = KeeperInterface.createCommomUser();
        }
        source.setEmail(userEmail);
        crudController.userCRUD.updateUser(stateController.getCurrentUser().getUuid(), source);
    }
    public void updateUserPassword(String userPassword){
        User source;
        if(stateController.getCurrentUser() instanceof AdminUser){
            source = KeeperInterface.createAdminUser();
        } else {
            source = KeeperInterface.createCommomUser();
        }
        source.setPassword(userPassword);
        crudController.userCRUD.updateUser(stateController.getCurrentUser().getUuid(), source);
    }


}
