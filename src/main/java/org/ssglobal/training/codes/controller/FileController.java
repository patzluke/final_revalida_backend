package org.ssglobal.training.codes.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/file")
public class FileController {

	@POST
	@Path("/insert/image")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadImage(
	    @FormDataParam("file") InputStream uploadedInputStream,
	    @FormDataParam("file") FormDataContentDisposition fileDetails) {
	   String uploadedFileLocation = "src/main/resources/static/images/" + fileDetails.getFileName();
	   
	   // save it
	   writeToFile(uploadedInputStream, uploadedFileLocation);

	   String output = "File uploaded to : " + uploadedFileLocation;

	   return Response.ok(output).status(Status.OK).build();
	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
	   try (OutputStream out = new FileOutputStream(new File(uploadedFileLocation))) {
	      int read = 0;
	      byte[] bytes = new byte[1024];
	      while ((read = uploadedInputStream.read(bytes)) != -1) {
	         out.write(bytes, 0, read);
	      }
	      out.flush();
	   } catch (IOException e) {
	      e.printStackTrace();
	   }
	}
	
	public String getSourcePath() {
        java.nio.file.Path resourcePath = Paths.get(getClass().getClassLoader().getResource("application.properties").getPath());
        return resourcePath.getParent().toString();
    }
	
	@GET
    @Path("/display/image/{imageName:.+}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response displayImage(@PathParam("imageName") String imageName) {
        InputStream inputStream = getClass().getResourceAsStream("/static/images/" + imageName);
        if (inputStream != null) {
            return Response.ok(inputStream).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
