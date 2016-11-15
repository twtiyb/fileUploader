package cn.com.iscs.upload.action;

import cn.com.iscs.upload.entity.AbsResponse;
import cn.com.iscs.upload.util.FilesClientWrap;
import com.mosso.client.cloudfiles.FilesContainer;
import com.mosso.client.cloudfiles.FilesContainerInfo;
import com.mosso.client.cloudfiles.FilesObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@ResponseBody
@RequestMapping(value = "/file")
@RestController
public class UploadAction {
	public final static String FILE_CONTAINER = "temp";

	@Resource
	FilesClientWrap filesClient;

	/**
	 * 上传
	 *
	 * @param file
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//Todo 对上传文件的安全检查,文件头,文件类型等.
	@RequestMapping(value = "/upload")
	public AbsResponse upload(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request) throws Exception {
		String fileName = "";
		String fileType = "";
		String container = "";
		try {
			fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			//Todo 对类型进行校验
			fileName = UUID.randomUUID() + fileType;
			container = request.getParameter("path");
			if (StringUtils.isEmpty(container)) {
				container = FILE_CONTAINER;
			}
			filesClient.storeStreamedObject(container, file.getInputStream(), file.getContentType(), fileName, converMap(request.getParameterMap()));
		} catch (IOException e) {
			System.out.println(e.toString());
//			log.error(e);
		}
		AbsResponse abs = new AbsResponse();
		Map map = new HashMap();
		map.put("path", "/" + container + "/" + fileName);
		map.put("name", fileName);
		map.put("oName", file.getName());
		abs.setData(map);
		return abs;
	}

	/**
	 * 上传 多个文件
	 *
	 * @param files
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//Todo 对上传文件的安全检查,文件头,文件类型等.
	@RequestMapping(value = "/uploads")
	public AbsResponse uploads(@RequestParam(value = "file", required = true)MultipartFile files[], HttpServletRequest request) throws Exception {
		String fileName = "";
		String fileType = "";
		String container = "";
		List<Map> fielList = new ArrayList<>();
		for (MultipartFile file : files) {
			try {
				fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				//Todo 对类型进行校验
				fileName = UUID.randomUUID() + fileType;
				container = request.getParameter("path");
				if (StringUtils.isEmpty(container)) {
					container = FILE_CONTAINER;
				}
				filesClient.storeStreamedObject(container, file.getInputStream(), file.getContentType(), fileName, converMap(request.getParameterMap()));
				Map map = new HashMap();
				map.put("path", "/" + container + "/" + fileName);
				map.put("name", fileName);
				map.put("oName", file.getOriginalFilename());
				fielList.add(map);
			} catch (IOException e) {
				System.out.println(e.toString());
//			log.error(e);
			}
		}
		AbsResponse abs = new AbsResponse();
		abs.setData(fielList);
		return abs;
	}


	/**
	 * 上传修改文件
	 *
	 * @param file
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//Todo 对上传文件的安全检查,文件头,文件类型等.
	@RequestMapping(value = "/modify")
	public AbsResponse modify(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
		String fileName = "";
		String fileType = "";
		String container = "";
		try {
			fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			//Todo 对类型进行校验
			fileName = file.getOriginalFilename();
			container = request.getParameter("path");
			if (StringUtils.isEmpty(container)) {
				container = FILE_CONTAINER;
			}
			filesClient.storeStreamedObject(container, file.getInputStream(), file.getContentType(), fileName, converMap(request.getParameterMap()));
		} catch (IOException e) {
//			log.error(e);
			System.out.println(e.toString());
		}
		AbsResponse abs = new AbsResponse();
		Map map = new HashMap();
		map.put("path", "/" + container + "/" + fileName);
		map.put("name", fileName);
		map.put("oName", file.getOriginalFilename());
		abs.setData(map);
		return abs;
	}

	/**
	 * 查看目录下的文件
	 *
	 * @param path
	 * @param startFile
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public AbsResponse list(@RequestParam(value = "path") String path, @RequestParam(value = "startFile") String startFile,
	                        @RequestParam(value = "limit") int limit) throws Exception {
		AbsResponse abs = new AbsResponse();

		FilesContainerInfo container = filesClient.getContainerInfo(path);
		List<FilesObject> objList = filesClient.listObjects(path, limit, startFile);
		List<HashMap> filesList = new ArrayList<>();
		for (FilesObject obj : objList) {
			HashMap fileMap = new HashMap();
			fileMap.put("name", obj.getName());
			fileMap.put("md5sum", obj.getMd5sum());
			fileMap.put("size", obj.getSize());
			fileMap.put("mimeType", obj.getMimeType());
			fileMap.put("lastModified", obj.getLastModified());
			filesList.add(fileMap);
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("total", container.getTotalSize());
		map.put("curTotal", filesList.size());
		map.put("path", path);
		abs.setData(map);
		return abs;
	}

	/**
	 * 查看当前目录
	 *
	 * @param startFile
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listDir")
	public AbsResponse listDir(@RequestParam(value = "startFile") String startFile, @RequestParam(value = "limit") int limit) throws Exception {
		AbsResponse abs = new AbsResponse();
		List<FilesContainer> objList = filesClient.listContainers(limit, startFile);
		List<HashMap> filesList = new ArrayList<>();
		for (FilesContainer obj : objList) {
			HashMap fileMap = new HashMap();
			fileMap.put("name", obj.getName());
			fileMap.put("info", obj.getInfo());
			filesList.add(fileMap);
		}
		abs.setData(filesList);
		return abs;
	}

	/**
	 * 创建目录
	 *
	 * @param path
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createDir")
	public AbsResponse createDir(@RequestParam(value = "path") String path) throws Exception {
		AbsResponse abs = new AbsResponse();
		filesClient.createContainer(path);
		return abs;
	}

	/**
	 * 删除文件
	 *
	 * @param path
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del")
	public AbsResponse del(@RequestParam(value = "path") String path, @RequestParam(value = "name") String name) throws Exception {
		AbsResponse abs = new AbsResponse();
		filesClient.deleteObject(path, name);
		return abs;
	}

	private Map<String, String> converMap(Map parameterMap) {
		for (Object key : parameterMap.keySet()) {
			parameterMap.put(key, parameterMap.get(key).toString());
		}
		return parameterMap;
	}
}