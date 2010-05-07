package uk.ac.ebi.age.admin.server.mng;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import uk.ac.ebi.age.admin.server.service.UploadRequest;
import uk.ac.ebi.age.admin.server.user.Session;
import uk.ac.ebi.age.mng.SemanticManager;
import uk.ac.ebi.age.model.writable.SubmissionWritable;
import uk.ac.ebi.age.parser.AgeTabSemanticValidator;
import uk.ac.ebi.age.parser.AgeTabSubmission;
import uk.ac.ebi.age.parser.AgeTabSyntaxParser;
import uk.ac.ebi.age.storage.AgeStorageAdm;

import com.pri.util.stream.StreamPump;

public class SubmissionUploader implements UploadCommandListener
{
 private AgeStorageAdm storAdm;

 public SubmissionUploader(AgeStorageAdm storage)
 {
  storAdm=storage;
 }

 @Override
 public boolean processUpload(UploadRequest upReq, Session sess)
 {
  try
  {
   if(upReq.getFiles() == null)
    return false;

   String text = null;

   ByteArrayOutputStream bais = new ByteArrayOutputStream();
   if(upReq.getFiles().size() == 1)
   {
    FileInputStream fis = new FileInputStream(upReq.getFiles().get(0));

    StreamPump.doPump(fis, bais);
   }
   else
   {
    for(File f : upReq.getFiles())
    {
     FileInputStream fis = new FileInputStream(f);
     StreamPump.doPump(fis, bais, false);
     fis.close();

     bais.write('\n');
     bais.write('\n');
    }
   }
   text = new String(bais.toByteArray());

   AgeTabSubmission sbm = AgeTabSyntaxParser.getInstance().parse(text);
   SubmissionWritable submission = AgeTabSemanticValidator.getInstance().parse(sbm,
     SemanticManager.getInstance().getContextModel(sess.getUserProfile()));

   storAdm.storeSubmission(submission);
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }

  return true;
 }

}