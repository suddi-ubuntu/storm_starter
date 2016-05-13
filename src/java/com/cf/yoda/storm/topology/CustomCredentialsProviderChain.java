package com.cf.yoda.storm.topology;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;

/**
 * Credentials provider chain that is similar to
 * DefaultAWSCredentialsProviderChain, but also includes the
 * ClasspathPropertiesFileCredentialsProvider.
 */

public class CustomCredentialsProviderChain extends AWSCredentialsProviderChain {

	public CustomCredentialsProviderChain() {
		
		super(
				new ClasspathPropertiesFileCredentialsProvider()
				 );
	}

	public static void main(String[] args) {

		
		System.out.println(new ClasspathPropertiesFileCredentialsProvider("AwsCredentials.properties")
				.getCredentials().getAWSAccessKeyId());
		
		System.out.println(new EnvironmentVariableCredentialsProvider().getCredentials().getAWSAccessKeyId());
		System.out.println(new SystemPropertiesCredentialsProvider().getCredentials().getAWSAccessKeyId());
		System.out.println(new InstanceProfileCredentialsProvider().getCredentials().getAWSAccessKeyId());

	}

}
