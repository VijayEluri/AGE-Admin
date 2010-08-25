package uk.ac.ebi.age.admin.client;

import uk.ac.ebi.age.admin.client.model.ModelImprint;
import uk.ac.ebi.age.admin.client.model.ModelStorage;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AgeAdminServiceAsync
{

 void login(String uname, String pass, AsyncCallback<String> callback);

 void getModelImprint(AsyncCallback<ModelImprint> callback);

 void getModelStorage(AsyncCallback<ModelStorage> asyncCallback);

 void saveModel(ModelImprint model, String storePath, AsyncCallback<Void> asyncCallback);

}
