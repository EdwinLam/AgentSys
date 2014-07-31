package com.edwin.agentsys.index;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.edwin.agentsys.base.view.JsonView;

/**
 * 文件上传控制器
 * 
 * @author Daniel
 * 
 */
@Controller
@RequestMapping("/base.do")
public class UploadController  {

	/**
	 * 上传文件到临时目录
	 * @RequestParam("file")MultipartFile file 注意这个参数
	 * 根据文件表单名称获取上传的文件，封装在一个MultipartFile对象
	 * 可直接使用，其他参数跟普通表单一样获取，使用非常方便！
	 * @param san :上传的文件类型，为尽量避免js被篡改，故不使用fileType等名称，并在js文件中不作注释
	 */
	@RequestMapping(params = "action=upload")
	public ModelAndView uploadFile(HttpServletRequest request, @RequestParam("file")MultipartFile file,String san) throws Exception {
		String uploadDir = request.getSession().getServletContext().getRealPath("/") + "image"+File.separator+"product";
		//待办工作、学校公告  文件上传路径比较特殊，需使用地区缩写和学校id
		 if(file.getSize()>=(1024*1024*1.5)){
				System.out.println("上传的文件太大...");
				JsonView jsonView = new JsonView(false, "上传的文件太大（不能超过 1.5MB）,操作失败!");
				jsonView.setProperty("isNeedSpecialAlert", false);
				return new ModelAndView(jsonView);
		}
		if(!checkFileType(san,file.getOriginalFilename())){
			System.out.println("上传的文件类型不符要求...");
			JsonView jsonView = new JsonView(false, "上传的文件类型不符要求,操作失败!");
			return new ModelAndView(jsonView);
		}
		
		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			FileUtils.forceMkdir(dirPath);
		}
		
		String randomFileName = createRandomFileName(file.getOriginalFilename());//生成的随机文件名
		
		File uploadedFile = new File(uploadDir + File.separator + randomFileName);
		uploadedFile.createNewFile();
		FileCopyUtils.copy(file.getBytes(), uploadedFile);
		JsonView jsonView = new JsonView(true, "操作成功!");
		jsonView.setProperty("rfname", randomFileName);
		String fileURL = File.separator+"image"+File.separator+"product"+File.separator+randomFileName;
		jsonView.setProperty("savUrlPath",fileURL); //返回文件保存的地址，图片预览可以用此信息
		return new ModelAndView(jsonView);
	}
	
	
	/**
	 * 删除临时文件
	 * @param request
	 * @param moduleFlag 模块标识
	 * @param fileName 文件名
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "action=removeTempFile")
	public ModelAndView removeTempFile(HttpServletRequest request, String moduleFlag, String fileName) throws IOException{
		String uploadDir = request.getSession().getServletContext().getRealPath("/") + "image";
		
		File uploadedFile = new File(uploadDir + File.separator + fileName);
		if(uploadedFile.isFile()){
			FileUtils.forceDelete(uploadedFile);
		}
		return new ModelAndView(new JsonView(true, fileName));
	}
	
	private String createRandomFileName(String originalFileName){
		return System.currentTimeMillis() + originalFileName.substring(originalFileName.lastIndexOf("."));
	}
	
	/**
	 * 解析允许的文件类型（如sjsln6s4arsvs3nmn解析为jpg|jpeg格式)
	 * @16种类型：0-xls 1-xlsx 2-jpg 3-jpeg 4-txt 5-png   6-  7-  8-  9-...多种类型按升序拼接：01-xls|xlsx 23-jpg|jpeg...
	 * @第一步转化(二进制)：0000-xls  0001-xlsx 0010-jpg 0011-jpeg 0100 txt,0101 png... 多种类型按升序拼接：0000|0001-xls|xlsx 0010|0011-jpg|jpeg...
	 * @第二步转化(0-s 1-n |-a):ssss-xls sssn-xlsx ssnn-jpeg ... 多种类型按升序拼接：ssssasssn-xls|xlsx ssnsassnn-jpg|jpeg...
	 * @第三步转化(隔位加符号,除s/a/n/特殊字符):s8s5sms-xls s4sgsrn-xlsx smsynhs-jpg ... 多种类型按升序拼接：s4smsxseajses1s0n-xls|xlsx sjsln6s4arsvs3nmn-jpg|jpeg...
	 * @param type
	 * @param name
	 * @return
	 */
	private boolean checkFileType(String type,String name){
		if(type==null||"-1".equals(type))//视为无类型限制
			return true;
		name = name.toLowerCase();
		String type2="";
		for(int i=0;i<type.length();i=i+2){
			type2+=type.substring(i, i+1);
		}
		String[] types=type2.split("a");
		for(int i=0;i<types.length;i++){
			
			if(TwoToOne(types[i])==0&&"xls".equals(name.substring(name.lastIndexOf(".")+1))) 
				return true;
			if(TwoToOne(types[i])==1&&"xlsx".equals(name.substring(name.lastIndexOf(".")+1))) 
				return true;
			if(TwoToOne(types[i])==2&&"jpg".equals(name.substring(name.lastIndexOf(".")+1))) 
				return true;
			if(TwoToOne(types[i])==3&&"jpeg".equals(name.substring(name.lastIndexOf(".")+1))) 
				return true;
			if(TwoToOne(types[i])==4&&"txt".equals(name.substring(name.lastIndexOf(".")+1))) 
				return true;
			if(TwoToOne(types[i])==5&&"png".equals(name.substring(name.lastIndexOf(".")+1))) 
				return true;
		}
		return false;
	}
	
	private int TwoToOne(String type){
		if("ssss".equals(type))
			return 0;
		if("sssn".equals(type))
			return 1;
		if("ssns".equals(type))
			return 2;
		if("ssnn".equals(type))
			return 3;
		if("snss".equals(type))
			return 4;
		if("snsn".equals(type))
			return 5;
		if("snns".equals(type))
			return 6;
		if("snnn".equals(type))
			return 7;
		if("nsss".equals(type))
			return 8;
		if("nssn".equals(type))
			return 9;
		if("nsns".equals(type))
			return 10;
		if("nsnn".equals(type))
			return 11;
		if("nnss".equals(type))
			return 12;
		if("nnsn".equals(type))
			return 13;
		if("nnns".equals(type))
			return 14;
		if("nnnn".equals(type))
			return 15;
		return -1;
	}
}
