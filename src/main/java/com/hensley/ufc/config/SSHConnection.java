//package com.hensley.ufc.config;
//
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.Session;
//
//public class SSHConnection {
//
//	private final static String S_PATH_FILE_PRIVATE_KEY = "C:\\Users\\Val\\.ssh\\privatekeyputy.ppk";
//	private final static String S_PATH_FILE_KNOWN_HOSTS = "C:\\Users\\Val\\.ssh\\known_hosts";
//	private final static String S_PASS_PHRASE = "1108Land!";
//	private final static int LOCAl_PORT = 5431;
//	private final static int REMOTE_PORT = 5432;
//	private final static int SSH_REMOTE_PORT = 22;
//	private final static String SSH_USER = "ehens86";
//	private final static String SSH_REMOTE_SERVER = "207.237.93.29";
//	private final static String MYSQL_REMOTE_SERVER = "192.168.1.2";
//
//	private Session sesion; // represents each ssh session
//
//	public void closeSSH() {
//		sesion.disconnect();
//	}
//
//	public SSHConnection() throws Throwable {
//
//		JSch jsch = null;
//
//		jsch = new JSch();
//		jsch.setKnownHosts(S_PATH_FILE_KNOWN_HOSTS);
//		jsch.addIdentity(S_PATH_FILE_PRIVATE_KEY, S_PASS_PHRASE.getBytes());
//
//		sesion = jsch.getSession(SSH_USER, SSH_REMOTE_SERVER, SSH_REMOTE_PORT);
//		sesion.connect(); // ssh connection established!
//
//		// by security policy, you must connect through a fowarded port
//		sesion.setPortForwardingL(LOCAl_PORT, MYSQL_REMOTE_SERVER, REMOTE_PORT);
//
//	}
//}