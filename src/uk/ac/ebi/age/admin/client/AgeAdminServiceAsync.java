package uk.ac.ebi.age.admin.client;

import java.util.List;

import uk.ac.ebi.age.admin.client.model.ModelImprint;
import uk.ac.ebi.age.admin.client.model.ModelStorage;
import uk.ac.ebi.age.admin.shared.ModelPath;
import uk.ac.ebi.age.ext.submission.SubmissionMeta;
import uk.ac.ebi.age.ext.submission.SubmissionQuery;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AgeAdminServiceAsync
{

 void login(String uname, String pass, AsyncCallback<String> callback);

 void getModelImprint(AsyncCallback<ModelImprint> callback);

 void getModelStorage(AsyncCallback<ModelStorage> asyncCallback);

 void saveModel(ModelImprint model, ModelPath storePath, AsyncCallback<Void> callback);

 void getModel(ModelPath path, AsyncCallback<ModelImprint> asyncCallback);

 void installModel(ModelPath modelPath, AsyncCallback<Void> asyncCallback);

 void getSubmissions(SubmissionQuery q, AsyncCallback<List<SubmissionMeta>> asyncCallback);

}
