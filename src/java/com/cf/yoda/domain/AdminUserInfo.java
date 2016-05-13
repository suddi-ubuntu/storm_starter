package com.cf.yoda.domain;

import com.jcraft.jsch.UserInfo;

public final class AdminUserInfo implements UserInfo {
	@Override
	public void showMessage(String message) {
		System.out.println(message);
	}

	@Override
	public boolean promptYesNo(String arg0) {
		return false;
	}

	@Override
	public boolean promptPassword(String arg0) {
		return false;
	}

	@Override
	public boolean promptPassphrase(String arg0) {
		return false;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getPassphrase() {
		return null;
	}
}