package uk.ac.ebi.age.admin.client;

import java.util.List;

import uk.ac.ebi.age.admin.client.model.ModelImprint;
import uk.ac.ebi.age.admin.client.model.ModelStorage;
import uk.ac.ebi.age.admin.client.model.ModelStorageException;
import uk.ac.ebi.age.admin.shared.ModelPath;
import uk.ac.ebi.age.ext.annotation.AnnotationDBException;
import uk.ac.ebi.age.ext.authz.TagRef;
import uk.ac.ebi.age.ext.entity.Entity;
import uk.ac.ebi.age.ext.log.LogNode;
import uk.ac.ebi.age.ext.log.SimpleLogNode;
import uk.ac.ebi.age.ext.submission.HistoryEntry;
import uk.ac.ebi.age.ext.submission.SubmissionDBException;
import uk.ac.ebi.age.ext.submission.SubmissionQuery;
import uk.ac.ebi.age.ext.submission.SubmissionReport;
import uk.ac.ebi.age.ext.user.exception.NotAuthorizedException;
import uk.ac.ebi.age.ext.user.exception.UserAuthException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ageAdminGWT")
public interface AgeAdminService extends RemoteService
{
 public static class Util
 {
  private static AgeAdminServiceAsync instance;
  
  public static AgeAdminServiceAsync getInstance()
  {
   if( instance != null )
    return instance;
   
   
   instance = (AgeAdminServiceAsync) GWT.create(AgeAdminService.class);
   return instance;
  }
 }
 
 String login(String uname, String pass) throws UserAuthException;
 
 ModelImprint getModelImprint() throws NotAuthorizedException;

 ModelStorage getModelStorage() throws NotAuthorizedException;

 void saveModel(ModelImprint model, ModelPath storePath) throws NotAuthorizedException, ModelStorageException;

 ModelImprint getModel(ModelPath path) throws NotAuthorizedException, ModelStorageException;

 LogNode installModel(ModelPath modelPath) throws NotAuthorizedException, ModelStorageException;

 SubmissionReport getSubmissions(SubmissionQuery q) throws NotAuthorizedException, SubmissionDBException;

 List<HistoryEntry> getSubmissionHistory(String sbmId) throws NotAuthorizedException, SubmissionDBException;

 SimpleLogNode deleteSubmission(String id) throws NotAuthorizedException, SubmissionDBException;

 SimpleLogNode restoreSubmission(String id) throws NotAuthorizedException, SubmissionDBException;


 SimpleLogNode tranklucateSubmission(String id) throws NotAuthorizedException,SubmissionDBException;

 List<TagRef> getEntityTags(Entity instance) throws NotAuthorizedException, AnnotationDBException;

 void storeEntityTags(Entity instance, List<TagRef> tr) throws NotAuthorizedException, AnnotationDBException;

 boolean setMaintenanceMode( boolean set, int timeout ) throws NotAuthorizedException;
}
