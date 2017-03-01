package com.example.filemanage;

import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

//import com.hoperun.adapter.FileListAdapter;

public class FileManagerActivity extends ListActivity {
    /** Called when the activity is first created. */
	private TextView showXPath;// ��ʾ�ļ��ļ�·��
	private ArrayList<String> items;// Ҫ��ʾ���ļ���
	private ArrayList<String> paths;// ��ʾ�ļ�·��
	private String rootPath = "/";// ��Ŀ¼
	private View renameDialogView;// �������Ի�����ͼ
	private EditText nameEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        showXPath = (TextView) findViewById(R.id.xPath);
        getFileDir(rootPath);// ��ȡ�ļ��б�
    }

    // ��ȡ�ļ��б�����
	private void getFileDir(String path) {
		showXPath.setText(path);//��ʾ��ǰ·��
		items = new ArrayList<String>();
		paths = new ArrayList<String>();
		// ��ȡ��ǰ·���µ��ļ�
		File presentFile = new  File(path);
		File[] files = presentFile.listFiles();
		
		if (! path.equals(rootPath)) {
			// ���ظ�Ŀ¼
			items.add("back to /");
			paths.add(rootPath);
			// ������һ��Ŀ¼
			items.add("back previous");
			paths.add(presentFile.getParent());
		}
		
		// ���ӵ�ǰ·���µ����е��ļ�����·��
		for (File f : files) {
			items.add(f.getName());
			paths.add(f.getPath());
		}
		
		// �����б�������
		setListAdapter(new FileListAdapter(FileManagerActivity.this, items, paths));
	}
	
	// List��item�ĵ���¼�
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		File f = new File(paths.get(position));
		if (f.isDirectory()) {
			getFileDir(paths.get(position));
			
		} else {
			fileHandle(f);
		}
	}

	// File����������
	private void fileHandle(final File f) {
		// ���ü������������ڵ����б��ļ�item��ʱ�򵯳��Ի���
		OnClickListener clickListener = new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {// ���ļ�
					openFile(f);
				} else if (which == 1) {// �޸��ļ���
					// �����޸��ļ����Ի���
					LayoutInflater renameInflater = LayoutInflater
						.from(FileManagerActivity.this);
					renameDialogView = renameInflater.inflate(
							R.layout.rename_alert_dialog, null);
					nameEdit = (EditText) renameDialogView.findViewById(R.id.nameEdit);
					// ���ñ༭������δ�ļ���
					nameEdit.setText(f.getName());
					
					// ��ѡ��Ի�����ѡ��Ҫ���еĲ������ڵ������������Ի����еļ�����
					OnClickListener renameDialogListener = new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							String nameEditStr = nameEdit.getText().toString();// ��ȡ�������༭���е��ı�����
							final String postRenamedFilePath = f.getParentFile().getPath() 
									+ "/" + nameEditStr;// ����������ļ�·��
							
							if (new File(postRenamedFilePath).exists()) {// �޸ĺ���ļ��������е��ļ�����ͻ
								// ������δ�޸��ļ�����ֱ�ӵ��ȷ�������
								if (! nameEditStr.equals(f.getName())) {
									new AlertDialog.Builder(FileManagerActivity.this)
										.setTitle("��ʾ").setMessage("�ļ��Ѿ����ڣ��Ƿ񸲸ǣ�")
										.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
											// ȷ������ԭ���ļ����еĲ���
											public void onClick(DialogInterface dialog,	int which) {
												f.renameTo(new File(postRenamedFilePath));// �����������ļ�
												getFileDir(f.getParent());// �г�����������
											}
											
										}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

											public void onClick(
													DialogInterface dialog,
													int which) {
											}
											
										}).show();
								} 
								
							} else {
								f.renameTo(new File(postRenamedFilePath));// �����������ļ�
								getFileDir(f.getParent());// �г�����������
							}
						}
					};
					
					// �������Ի���
					AlertDialog renameDialog =  new AlertDialog.Builder(FileManagerActivity.this).create();
					renameDialog.setView(renameDialogView);// �����������Ի������ͼ
					
					renameDialog.setButton("ȷ��", renameDialogListener);
					renameDialog.setButton2("ȡ��", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
						}
						
					});
					renameDialog.show();
					
				} else {
					// ɾ��ѡ�е��ļ�ѡ��
					new AlertDialog.Builder(FileManagerActivity.this).setTitle("ɾ��")
						.setMessage("ȷ��Ҫɾ�����ļ���").setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
							// ȷ��ɾ���ļ�
							public void onClick(DialogInterface dialog,
									int which) {
								f.delete();// ɾ���ļ�
								getFileDir(f.getParent());
							}
							
						}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
							}
							
						}).show();
				}
				
			}
			
		};
		
		// ����listDialog,ѡ��Ի���
		String[] operas = new String[] {"open", "rename", "delete"};
		new AlertDialog.Builder(FileManagerActivity.this)
			.setTitle("operator dialog").setItems(operas, clickListener)
			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
				}
				
			}).show();
	}

	// �ļ��򿪷���
	private void openFile(File f) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		
		// ��ȡ�ļ�ý������
		String type = getMIMEType(f);
		intent.setDataAndType(Uri.fromFile(f), type);
		startActivity(intent);
	}

	// ��ȡMIME Type����
	private String getMIMEType(File f) {
		String type = "";
		String fileName = f.getName();
		String end = fileName.substring(fileName.indexOf(".") + 1).toLowerCase();
		// �ж��ļ�����
		if(end.equals("m4a") || end.equals("mp3") || end.equals("mid") 
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
			type = "audio"; 
	    } else if (end.equals("3gp") || end.equals("mp4")) {
	    	type = "video";
	    } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") 
	    		|| end.equals("jpeg") || end.equals("bmp")) {
	    	type = "image";
	    } else {
	      type="*";
	    }
		// MIME Type��ʽ��"�ļ�����/�ļ���չ��"
		type += "/*";
		return type;
	}
}