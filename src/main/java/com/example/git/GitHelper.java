package com.example.git;

import java.io.File;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GitHelper {

	/**
	 * 克隆仓库
	 * 
	 * @param uri       仓库地址
	 * @param directory 保存克隆仓库的地址
	 * @return 是否成功
	 */
	@SuppressWarnings("unused")
	private static boolean cloneRepository(String uri, String directory) {
		try {
			CloneCommand cloneCommand = Git.cloneRepository().setURI(uri);
			cloneCommand.setDirectory(new File(directory)).call();
			return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return false;
	}

	/**
	 * 拉取分支
	 * 
	 * @param remoteBranchName 远程分支名
	 * @param dir              本地仓库目录
	 * @return 是否成功
	 */
	@SuppressWarnings("unused")
	private static boolean pull(String remoteBranchName, String dir) {
		try {
			Git git = Git.open(new File(dir));
			git.pull().setRemoteBranchName(remoteBranchName).call();
			return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return false;
	}

	/**
	 * 提交仓库
	 * 
	 * @param message 本次提交的注释
	 * @param dir     本地仓库路径
	 * @return 是否成功
	 */
	@SuppressWarnings("unused")
	private static boolean commit(String message, String dir) {
		try {
			Git git = Git.open(new File(dir));
			git.commit().setMessage(message).call();
			return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return false;
	}

	/**
	 * 推送
	 * 
	 * @param dir 本地仓库路径
	 * @return 是否成功
	 */
	@SuppressWarnings("unused")
	private static boolean push(String dir) {
		try {
			Git git = Git.open(new File(dir));
			git.push().call();
			return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return false;
	}

	public static void main(String[] args) {
		String uri = "https://github.com/wangzijoe/Japanese.git";
		String directory = "D://cache/jap";
		cloneRepository(uri, directory);
		pull("master", directory);
	}
}
