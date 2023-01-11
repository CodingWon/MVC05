package kr.bit.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileAddController implements Controller {

	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String UPLOAD_DIR ="file_reop";
		String uploadPath = request.getServletContext().getRealPath("")+File.separator+UPLOAD_DIR;
		File currentDirPath = new File(uploadPath); // 업로드할 경로를 File 객체로 만들기
		
		if(!currentDirPath.exists()) {
			currentDirPath.mkdir();
		}
		
		/*Client 파일 업로드 시
		 	- 파일 크기가 클 시 jvm 메모리에 저장하기 힘듬
		 	방법 1)임시 디렉터리에다 만들어 놓기(임시 directroy 를 실제 directory 처럼 사용하기도 한다)
		 	방법 2) 임시 디렉터리 내용을 실제 디렉터리에다가 저장
		 * */
		
		// 파일을 업로드 할때 먼저 저장될 임시 저장경로를 설정
		//file upload시 필요한 API commons-fileupload-1.3.3 , commons-io-2.6
		DiskFileItemFactory factory =new DiskFileItemFactory();
		factory.setRepository(currentDirPath);
		factory.setSizeThreshold(1024*1024); // 최대 용량
		
		String fileName = null;
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
				//items -> FileItem[ ], FileItem[ ], FileItem[ ]
			List<FileItem> items =  upload.parseRequest(request); //request안에 여러개의 파일이 업로드 된 경우
			for(int i =0; i<items.size(); i++) {
				FileItem fileItem = items.get(i);
				if(fileItem.isFormField()) {
					System.out.println(fileItem.getFieldName() + " = " + fileItem.getString("utf-8"));
				}else {
					
					if(fileItem.getSize() > 0) {
						int idx = fileItem.getName().lastIndexOf("\\"); //  \\(window) 
						
						if(idx == -1) {
							idx = fileItem.getName().lastIndexOf("/"); // /(Linux)
						}
						fileName = fileItem.getName().substring(idx+1);
						File uploadFile = new File(currentDirPath+"\\"+fileName);
						// 파일 중복 체크
						if(uploadFile.exists()) {
							fileName =System.currentTimeMillis() + "_" + fileName;
							uploadFile = new File(currentDirPath+"\\"+fileName);
						}
						
						fileItem.write(uploadFile); // 임시경로 -> 새로운 경로에 파일 쓰기
					}
					
				}
				// 파일 및 파라미터 같이 넘어 올 경우
			}// for END
		}catch (Exception e) {
			
		}
		//$.ajax() 쪽으로 업로드 된 최종 파일이름을 전송시켜준다.
		response.setContentType("text/html;charset=euc-kr");
		response.getWriter().print(fileName);
		
		return null;
	}

	
}
