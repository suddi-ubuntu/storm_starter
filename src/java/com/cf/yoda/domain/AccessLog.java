package com.cf.yoda.domain;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cf.yoda.util.YodaUtils;

public class AccessLog {

	@JsonProperty("@version")
	private String version;
	
	@JsonProperty("@timestamp")
	private String system_timestamp;
	
	private String file;
	private String host;
	private String offset;
	private String type;
	private String client_ip;
	private String remote_logname;
	private String remote_user;

	private String time_stamp;
	private String method;
	private String request;
	private String responsetype;
	private String responsesize;
	private String referer;
	private String user_agent;
	private String server_name;
	private String response_time;
	private String uuid;
	private String sessionCountC;
	private String sessionCountS;
	private String phpSessionId;
	private String pid;
	private String cfkv;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSystem_timestamp() {
		return system_timestamp;
	}

	public void setSystem_timestamp(String system_timestamp) {
		this.system_timestamp = system_timestamp;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClient_ip() {
		return client_ip;
	}

	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}

	public String getRemote_logname() {
		return remote_logname;
	}

	public void setRemote_logname(String remote_logname) {
		this.remote_logname = remote_logname;
	}

	public String getRemote_user() {
		return remote_user;
	}

	public void setRemote_user(String remote_user) {
		this.remote_user = remote_user;
	}

	public String getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(String time_stamp) {
		this.time_stamp = time_stamp;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponsetype() {
		return responsetype;
	}

	public void setResponsetype(String responsetype) {
		this.responsetype = responsetype;
	}

	public String getResponsesize() {
		return responsesize;
	}

	public void setResponsesize(String responsesize) {
		this.responsesize = responsesize;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getUser_agent() {
		return user_agent;
	}

	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}

	public String getServer_name() {
		return server_name;
	}

	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

	public String getResponse_time() {
		return response_time;
	}

	public void setResponse_time(String response_time) {
		this.response_time = response_time;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSessionCountC() {
		return sessionCountC;
	}

	public void setSessionCountC(String sessionCountC) {
		this.sessionCountC = sessionCountC;
	}

	public String getSessionCountS() {
		return sessionCountS;
	}

	public void setSessionCountS(String sessionCountS) {
		this.sessionCountS = sessionCountS;
	}

	public String getPhpSessionId() {
		return phpSessionId;
	}

	public void setPhpSessionId(String phpSessionId) {
		this.phpSessionId = phpSessionId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCfkv() {
		return cfkv;
	}

	public void setCfkv(String cfkv) {
		this.cfkv = cfkv;
	}

	public String toString() {
		return YodaUtils.toJsonString(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cfkv == null) ? 0 : cfkv.hashCode());
		result = prime * result + ((client_ip == null) ? 0 : client_ip.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((offset == null) ? 0 : offset.hashCode());
		result = prime * result + ((phpSessionId == null) ? 0 : phpSessionId.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		result = prime * result + ((referer == null) ? 0 : referer.hashCode());
		result = prime * result + ((remote_logname == null) ? 0 : remote_logname.hashCode());
		result = prime * result + ((remote_user == null) ? 0 : remote_user.hashCode());
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result + ((response_time == null) ? 0 : response_time.hashCode());
		result = prime * result + ((responsesize == null) ? 0 : responsesize.hashCode());
		result = prime * result + ((responsetype == null) ? 0 : responsetype.hashCode());
		result = prime * result + ((server_name == null) ? 0 : server_name.hashCode());
		result = prime * result + ((sessionCountC == null) ? 0 : sessionCountC.hashCode());
		result = prime * result + ((sessionCountS == null) ? 0 : sessionCountS.hashCode());
		result = prime * result + ((system_timestamp == null) ? 0 : system_timestamp.hashCode());
		result = prime * result + ((time_stamp == null) ? 0 : time_stamp.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((user_agent == null) ? 0 : user_agent.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccessLog other = (AccessLog) obj;
		if (cfkv == null) {
			if (other.cfkv != null)
				return false;
		} else if (!cfkv.equals(other.cfkv))
			return false;
		if (client_ip == null) {
			if (other.client_ip != null)
				return false;
		} else if (!client_ip.equals(other.client_ip))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (offset == null) {
			if (other.offset != null)
				return false;
		} else if (!offset.equals(other.offset))
			return false;
		if (phpSessionId == null) {
			if (other.phpSessionId != null)
				return false;
		} else if (!phpSessionId.equals(other.phpSessionId))
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		if (referer == null) {
			if (other.referer != null)
				return false;
		} else if (!referer.equals(other.referer))
			return false;
		if (remote_logname == null) {
			if (other.remote_logname != null)
				return false;
		} else if (!remote_logname.equals(other.remote_logname))
			return false;
		if (remote_user == null) {
			if (other.remote_user != null)
				return false;
		} else if (!remote_user.equals(other.remote_user))
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		if (response_time == null) {
			if (other.response_time != null)
				return false;
		} else if (!response_time.equals(other.response_time))
			return false;
		if (responsesize == null) {
			if (other.responsesize != null)
				return false;
		} else if (!responsesize.equals(other.responsesize))
			return false;
		if (responsetype == null) {
			if (other.responsetype != null)
				return false;
		} else if (!responsetype.equals(other.responsetype))
			return false;
		if (server_name == null) {
			if (other.server_name != null)
				return false;
		} else if (!server_name.equals(other.server_name))
			return false;
		if (sessionCountC == null) {
			if (other.sessionCountC != null)
				return false;
		} else if (!sessionCountC.equals(other.sessionCountC))
			return false;
		if (sessionCountS == null) {
			if (other.sessionCountS != null)
				return false;
		} else if (!sessionCountS.equals(other.sessionCountS))
			return false;
		if (system_timestamp == null) {
			if (other.system_timestamp != null)
				return false;
		} else if (!system_timestamp.equals(other.system_timestamp))
			return false;
		if (time_stamp == null) {
			if (other.time_stamp != null)
				return false;
		} else if (!time_stamp.equals(other.time_stamp))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (user_agent == null) {
			if (other.user_agent != null)
				return false;
		} else if (!user_agent.equals(other.user_agent))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	
	
}
