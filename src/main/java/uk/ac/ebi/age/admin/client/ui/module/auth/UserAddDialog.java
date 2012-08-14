package uk.ac.ebi.age.admin.client.ui.module.auth;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;

public class UserAddDialog extends Window
{
 
 private final CloseClickHandler clsHnd = new CloseClickHandler()
 {
  @Override
  public void onCloseClick(CloseClickEvent event)
  {
   close();
  }
 };

 public UserAddDialog(DataSource ds)
 {
//  setWidth(300);
//  setHeight(200);
  setTitle("Add user");
  setShowMinimizeButton(false);
  setIsModal(true);
  setShowModalMask(true);
//  centerInPage();
  setAutoSize(true);
  setAutoCenter(true);

  addCloseClickHandler( clsHnd );
  
  addItem( new UserAddForm(ds,clsHnd) );
 }

 public void close()
 {
  destroy();
 }

}
