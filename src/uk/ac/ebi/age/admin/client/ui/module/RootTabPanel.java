package uk.ac.ebi.age.admin.client.ui.module;

import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class RootTabPanel extends TabSet
{
 public RootTabPanel()
 {
  setTabBarPosition(Side.TOP);  
  setWidth100();  
  setHeight100();  

  
  Tab submTab = new Tab("Submission");
  submTab.setPane( new SubmissionPanel() );
  
  addTab(submTab);

  Tab modelTab = new Tab("Model");
  modelTab.setPane( new ModelPanel() );
  
  addTab(modelTab);

 }
}