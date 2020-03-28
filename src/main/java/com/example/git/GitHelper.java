package com.example.git;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteListCommand;
import org.eclipse.jgit.api.StatusCommand;
import org.eclipse.jgit.api.SubmoduleInitCommand;
import org.eclipse.jgit.api.SubmoduleUpdateCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unused")
public class GitHelper {

	private static final String USER;
	private static final String PWD;
	private static final UsernamePasswordCredentialsProvider credential;

	static {
		USER = "wangzijoe@qq.com";
		PWD = "wangzijoe,6597";
		credential = new UsernamePasswordCredentialsProvider(USER, PWD);
	}

	public static void main(String[] args) {
//		cloneRepository("https://gitee.com/njnode/WorkNotes.git", "D://cache/WorkNotes");
//		commitRepository("D://cache/WorkNotes", "新建文本文档.txt", "2020-03-29");
//		pushRepository("D://cache/WorkNotes");
//		log("D://cache/WorkNotes");
	}

	public static String cloneRepository(String url, String localPath) {
		try {
			CloneCommand cloneCommand = Git.cloneRepository().setURI(url);
			cloneCommand.setCredentialsProvider(credential);
			cloneCommand.setDirectory(new File(localPath)).call();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public static String commitRepository(String localPath, String fileNames, String message) {
		try {
			Git git = Git.open(new File(localPath));
			AddCommand addCommand = git.add();
			String[] fileArr = fileNames.split(",");
			for (String file : fileArr) {
				addCommand.addFilepattern(file);
			}
			addCommand.call();
			
			CommitCommand commitCommand = git.commit();
			commitCommand.setMessage(message);
			commitCommand.call();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public static String pushRepository(String localPath) {
		try {
			Git git = Git.open(new File(localPath));
			PushCommand pushCommand = git.push();
			pushCommand.setCredentialsProvider(credential);
			pushCommand.setForce(true);
			Iterable<PushResult> iterable = pushCommand.call();
			
			for (PushResult pushResult : iterable) {
				System.err.println(JSON.toJSONString(pushResult, true));
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public static void log(String localPath) {
		try {
			Git git = Git.open(new File(localPath));
			LogCommand logCommand = git.log();
			Iterable<RevCommit> list = logCommand.call();
			for (RevCommit revCommit : list) {
				System.err.println(revCommit.getFullMessage());
				System.err.println(revCommit.getName());
			}
		} catch (Exception e) {

		}
	}
	
	

	/******************************************************************************************/



	public static void getChangedFiles(String localPath) {
		try {
			Git git = Git.open(new File(localPath));
			StatusCommand statusCommand = git.status();
			org.eclipse.jgit.api.Status status = statusCommand.call();
			Map<String, Set<String>> result = new HashMap<String, Set<String>>();
			Set<String> addedSet = status.getAdded();
			result.put("added", addedSet);
			Set<String> changedSet = status.getChanged();
			result.put("changed", changedSet);
			Set<String> missSet = status.getMissing();
			result.put("missed", missSet);
			System.err.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * fetch
	 * 
	 * @param localPath
	 * @param branch
	 * @return
	 */
	public static String fetchBranch(String localPath) {
		try {
			Git git = Git.open(new File(localPath));
			git.fetch().call();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 切换分支<br>
	 * 首先判断本地是否已有此分支
	 * 
	 * @param localPath 主仓
	 * @return
	 */
	public static String switchBranch(String localPath, String branch) {
		try {
			Git git = Git.open(new File(localPath));
			String newBranch = branch.substring(branch.lastIndexOf("/") + 1, branch.length());
			CheckoutCommand checkoutCommand = git.checkout();
			List<String> list = getLocalBranchNames(localPath);
			if (!list.contains(newBranch)) {// 如果本地分支
				checkoutCommand.setStartPoint(branch);
				checkoutCommand.setCreateBranch(true);
			}
			checkoutCommand.setName(newBranch);
			checkoutCommand.call();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 切换子仓的分支
	 * 
	 * @param localPath 主仓
	 * @param sub       子仓
	 * @param branch    分支名
	 * @return
	 */
	public static String switchSubhBranch(String localPath, String sub, String branch) {
		try {
			Git git = Git.open(new File(localPath + "\\.git\\modules" + sub));
			String newBranch = branch.substring(branch.lastIndexOf("/") + 1, branch.length());
			CheckoutCommand checkoutCommand = git.checkout();
			List<String> list = getLocalBranchNames(localPath + "\\.git\\modules" + sub);
			if (!list.contains(newBranch)) {// 如果本地分支
				checkoutCommand.setStartPoint(branch);
				checkoutCommand.setCreateBranch(true);
				checkoutCommand.setForce(true);
			}
			checkoutCommand.setName(newBranch);
			checkoutCommand.call();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 拿到当前本地分支名
	 * 
	 * @param localPath 主仓
	 * @return
	 * @throws IOException
	 */
	public static String getCurrentBranch(String localPath) throws IOException {
		Git git = Git.open(new File(localPath));
		return git.getRepository().getBranch();
	}

	/**
	 * 拿到当前远程分支名
	 * 
	 * @param localPath 主仓
	 * @return
	 * @throws IOException
	 */
	public static String getCurrentRemoteBranch(String localPath) throws IOException {
		Git git = Git.open(new File(localPath));
		StoredConfig storedConfig = git.getRepository().getConfig();
		String currentRemote = storedConfig.getString("branch", getCurrentBranch(localPath), "remote");
		return currentRemote;
	}

	/**
	 * 获取所有远程
	 * 
	 * @param localPath 主仓
	 * @return
	 * @throws IOException
	 * @throws GitAPIException
	 */
	public static List<String> getRemotes(String localPath) throws IOException, GitAPIException {
		Git git = Git.open(new File(localPath));
		RemoteListCommand remoteListCommand = git.remoteList();
		List<RemoteConfig> list = remoteListCommand.call();
		List<String> result = new LinkedList<String>();
		for (RemoteConfig remoteConfig : list) {
			result.add(remoteConfig.getName());
		}
		return result;
	}

	/**
	 * 获取本地所有分支名
	 * 
	 * @param localPath
	 * @return
	 * @throws IOException
	 */
	public static List<String> getLocalBranchNames(String localPath) throws IOException {
		List<String> result = new LinkedList<String>();
		Git git = Git.open(new File(localPath));
		Map<String, Ref> map = git.getRepository().getAllRefs();
		Set<String> keys = map.keySet();
		for (String str : keys) {
			if (str.indexOf("refs/heads") > -1) {
				String el = str.substring(str.lastIndexOf("/") + 1, str.length());
				result.add(el);
			}
		}
		return result;
	}

	/**
	 * 根据名称获取所有远程分支
	 * 
	 * @param localPath 主仓
	 * @param name      远程名字
	 * @return
	 * @throws IOException
	 */
	public static List<String> getRemoteBranchNames(String localPath, String remote) throws IOException {
		List<String> result = new LinkedList<String>();
		Git git = Git.open(new File(localPath));
		Map<String, Ref> map = git.getRepository().getAllRefs();
		Set<String> keys = map.keySet();
		String index = "refs/remotes/" + remote;
		for (String str : keys) {
			if (str.indexOf(index) > -1) {
				// String el=str.substring(str.lastIndexOf("/")+1, str.length());
				result.add(str);
				System.err.println(str);
			}
		}
		return result;
	}

	/**
	 * 切换本地分支
	 * 
	 * @param localPath
	 * @param branch
	 * @return
	 */
	public static String switchLocalBranch(String localPath, String branch) {
		try {
			Git git = Git.open(new File(localPath));
			Repository repository = git.getRepository();
			ListBranchCommand listBranchCommand = git.branchList();
			List<Ref> ll = listBranchCommand.setListMode(ListMode.ALL).call();
			for (Ref ref : ll) {
				System.err.println(ref.getObjectId().toString());
			}

			RemoteListCommand remoteListCommand = git.remoteList();
			List<RemoteConfig> list = remoteListCommand.call();
			for (RemoteConfig remoteConfig : list) {
				System.err.println(remoteConfig.getName());
			}

			// 得到仓库本地分支
			String currentbranch = repository.getBranch();
			LsRemoteCommand lsRemoteCommand = Git.lsRemoteRepository();
			Collection<Ref> collection = lsRemoteCommand.call();
			for (Ref ref : collection) {
				System.err.println(ref.getName());
			}

			Map<String, Ref> map = repository.getAllRefs();
			Set<String> keys = map.keySet();
			for (String string : keys) {
				System.err.println(string);
			}
			Collection<Ref> values = map.values();
			for (Ref ref : values) {
				Storage storage = ref.getStorage();
				System.err.println(ref.getName());
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * push到远程仓库<br>
	 * 
	 * @param string
	 * @param string2
	 * @param string3
	 * @return
	 */


	/**
	 * 远程提交子仓
	 * 
	 * @param localPath 主仓
	 * @param sub       子仓
	 * @return
	 */
	public static String pushSubRepository(String localPath, String sub) {
		try {
			String newPath = localPath + "\\.git\\modules";
			Git git = Git.open(new File(newPath + sub));
			PushCommand pushCommand = git.push();
			CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider("15862663403@163.com",
					"yoyosd619");
			pushCommand.setCredentialsProvider(credentialsProvider);
			pushCommand.setForce(true).setPushAll();
			Iterable<PushResult> iterable = pushCommand.call();
			for (PushResult pushResult : iterable) {
				System.err.println(pushResult.toString());
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 提交本地代码
	 * 
	 * @param localPath 主仓
	 * @param fileNames 文件名集合
	 * @param message   备注
	 * @return
	 */


	/**
	 * @param localPath 主仓
	 * @param sub       子仓
	 * @param fileNames 文件名
	 * @param message   消息
	 * @return
	 */
	public static String commitSubRepository(String localPath, String sub, String fileNames, String message) {
		try {
			String newPath = localPath + "\\.git\\modules";
			Git git = Git.open(new File(newPath + sub));

			AddCommand addCommand = git.add();
			String[] fileArr = fileNames.split(",");
			for (String file : fileArr) {
				addCommand.addFilepattern(file);
			}
			addCommand.call();
			CommitCommand commitCommand = git.commit();
			commitCommand.setMessage(message);
			commitCommand.setAllowEmpty(true);
			commitCommand.call();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 更新主仓
	 * 
	 * @param localPath 主仓
	 * @return
	 */


	/**
	 * 更新子仓
	 * 
	 * @param sub       子仓名，多个时用，隔开
	 * @param localPath 主仓
	 * @return
	 */
	public static String pullSubRepository(String localPath, String sub) {
		try {
			String newPath = localPath + "\\.git\\modules";
			String[] subArr = sub.split(",");
			for (String path : subArr) {
				Git git = Git.open(new File(newPath + path));
				PullCommand pullCommand = git.pull();
				pullCommand.call();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 下载子仓
	 * 
	 * @param localPath 主仓
	 * @param sub       子仓
	 * @return
	 */
	public static String cloneSubRepository(String localPath, String sub) {
		try {
			File zicang = new File(localPath + "\\.git\\modules\\" + sub);
			if (zicang.exists()) {
				deleteDir(zicang);
			}
			Git git = Git.open(new File(localPath));
			System.err.println("开始下载子仓。。。");
			SubmoduleInitCommand submoduleInit = git.submoduleInit();
			SubmoduleUpdateCommand submoduleUpdate = git.submoduleUpdate();
			String[] submoduleArr = sub.split(",");
			for (String s : submoduleArr) {
				System.err.println("准备下载子仓：" + s);
				submoduleInit.addPath(s);
				submoduleUpdate.addPath(s);
			}
			submoduleInit.call();
			submoduleUpdate.call();
			System.err.println("子仓下载完成。。。");
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

	}

	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

}
