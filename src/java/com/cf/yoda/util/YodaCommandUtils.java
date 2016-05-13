package com.cf.yoda.util;

import java.io.File;
import java.nio.channels.Channel;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.ssh.Scp;

import com.cf.yoda.domain.AdminUserInfo;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class YodaCommandUtils {

	public static void main(String[] args) throws Exception {
		scpTo("/Users/pradyumnakumarjena/cf_yoda_git_repo/cf_yoda/data_pipeline_core/src/test/java/com/cf/yoda/util/YodaUtilsTest.java",
				"/home/pradyumnakumarjena", "172.", "pradyumnakumarjena");
	}

	public static void scpTo(String localfilePath, String remoteDirPath, String remoteHost, String userName)
			throws Exception {

		Scp scp = new Scp();
		scp.setTrust(true);
		Project project = getAntProject();
		scp.setProject(project);
		scp.setFile(localfilePath);
		scp.setRemoteTodir(getRemoteToDirPath(remoteDirPath, remoteHost, userName));
		scp.setKeyfile(getPrivatekeyPath());
		scp.execute();

	}

	private static String getRemoteToDirPath(String remoteDirPath, String remoteHost, String userName) {
		return userName + "@" + remoteHost + ":" + remoteDirPath;
	}

	private static Project getAntProject() {
		Project project = new Project();
		project.setBaseDir(new File(System.getProperty("user.home")));
		return project;
	}

	public static String getPrivatekeyPath() {
		return System.getProperty("user.home") + File.separator + ".ssh" + File.separator + "id_rsa";
	}
}
