package uk.ac.ebi.age.admin.server.mng;

import java.io.File;

import uk.ac.ebi.age.admin.client.common.ModelPath;
import uk.ac.ebi.age.admin.client.common.user.exception.UserAuthException;
import uk.ac.ebi.age.admin.client.model.ModelImprint;
import uk.ac.ebi.age.admin.client.model.ModelStorage;
import uk.ac.ebi.age.admin.client.model.ModelStorageException;
import uk.ac.ebi.age.admin.server.model.Age2ImprintConverter;
import uk.ac.ebi.age.admin.server.user.Session;
import uk.ac.ebi.age.admin.server.user.SessionPool;
import uk.ac.ebi.age.admin.server.user.UserDatabase;
import uk.ac.ebi.age.admin.server.user.UserProfile;
import uk.ac.ebi.age.admin.server.user.impl.SessionPoolImpl;
import uk.ac.ebi.age.admin.server.user.impl.TestUserDataBase;
import uk.ac.ebi.age.admin.server.util.DirectoryTools;
import uk.ac.ebi.age.model.SemanticModel;
import uk.ac.ebi.age.storage.AgeStorageAdm;


public class AgeAdmin
{
 private static AgeAdmin instance;

 public static AgeAdmin getDefaultInstance()
 {
  return instance;
 }

 private SessionPool   spool;
 private UserDatabase  udb;
 private AgeStorageAdm storage;
 private Configuration configuration;

 public AgeAdmin(Configuration conf, AgeStorageAdm storage)
 {
  configuration=conf;
  
  this.storage = storage;

  if(conf.getTmpDir() == null)
   conf.setTmpDir(new File("var/tmp"));

  if(conf.getSessionPool() == null)
   conf.setSessionPool(spool = new SessionPoolImpl());

  if(conf.getUserDatabase() == null)
   conf.setUserDatabase(udb = new TestUserDataBase());

  if(conf.getUploadManager() == null)
   conf.setUploadManager(new UploadManager());

  conf.getUploadManager().addUploadCommandListener("SetModel", new SemanticUploader(storage));
  conf.getUploadManager().addUploadCommandListener("Submission", new SubmissionUploader(storage));

  if(instance == null)
   instance = this;
 }

 public void shutdown()
 {
  if(spool != null)
   spool.shutdown();

  if(udb != null)
   udb.shutdown();
 }

 public Session login(String userName, String password, String clientAddr) throws UserAuthException
 {
//  UserDatabase udb = Configuration.getDefaultConfiguration().getUserDatabase();

  UserProfile prof = null;

  if(userName == null || userName.length() == 0)
   prof = udb.getAnonymousProfile();
  else
  {
   prof = udb.getUserProfile(userName);

   if(prof == null)
    throw new UserAuthException("Invalid user name");

   if(!prof.checkPassword(password))
    throw new UserAuthException("Invalid user password");

  }

  Session sess = spool.createSession(prof, new String[] { userName, clientAddr });

  return sess;
 }

 public ModelImprint getModelImprint( )
 {
  SemanticModel sm = storage.getSemanticModel();
  
  return Age2ImprintConverter.getModelImprint(sm);
 }

 public Session getSession(String value)
 {
  return spool.getSession(value);
 }

 public ModelStorage getModelStorage(Session userSession)
 {
  ModelStorage stor = new ModelStorage();
  
  stor.setPublicDirectory( DirectoryTools.imprintDirectory( configuration.getPublicModelDir() ) );
  stor.setUserDirectory( DirectoryTools.imprintDirectory( new File( configuration.getUserBaseDir(),
    String.valueOf(userSession.getUserProfile().getUserId())+File.separatorChar+configuration.getModelRelPath()) ) );
  
  return stor;
 }

 public void saveModel(ModelImprint model, ModelPath storePath, Session userSession) throws ModelStorageException
 {
  // TODO Auto-generated method stub
 }

 
}
