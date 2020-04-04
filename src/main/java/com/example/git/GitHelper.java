package com.example.git;

import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteListCommand;
import org.eclipse.jgit.api.RmCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.StatusCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unused")
public class GitHelper {

	private static String POINT_GIT = ".git";
	private static String ADDED = "added";
	private static String REMOVED = "removed";
	private static String MISSING = "missing";
	private static String MODIFIED = "modified";
	private static String CHANGED = "changed";

	private static String USER;
	private static String PWD;
	private static String LOCAL_REPOSITORY = "D://cache/WorkNotes";
	private static String REMOTE_REPOSITORY = "https://gitee.com/njnode/WorkNotes.git";
	private static UsernamePasswordCredentialsProvider CREDENTIAL;

	public static void main(String[] args) {
		signInAndPush();
	}

	private static void signInAndPush() {
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 667, 453);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Label usernameLabel = new Label("USER:");
		usernameLabel.setAlignment(Label.CENTER);
		usernameLabel.setBounds(116, 49, 50, 23);
		frame.getContentPane().add(usernameLabel);

		Label passwordLabel = new Label("PWD:");
		passwordLabel.setAlignment(Label.CENTER);
		passwordLabel.setBounds(116, 85, 50, 23);
		frame.getContentPane().add(passwordLabel);

		JButton button = new JButton("login");
		button.setBackground(new Color(255, 255, 255));
		button.setBounds(126, 121, 212, 23);
		frame.getContentPane().add(button);

		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(172, 49, 166, 23);
		frame.getContentPane().add(formattedTextField);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(172, 85, 166, 23);
		frame.getContentPane().add(passwordField);

		button.registerKeyboardAction(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					USER = formattedTextField.getText();
					PWD = new String(passwordField.getPassword());
					CREDENTIAL = new UsernamePasswordCredentialsProvider(USER, PWD);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					commitAndPush(LOCAL_REPOSITORY, df.format(new Date()));
				}catch(Exception exception){
					exception.printStackTrace();
				}finally {
					frame.setVisible(false);
					System.exit(0);
				}
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					USER = formattedTextField.getText();
					PWD = new String(passwordField.getPassword());
					CREDENTIAL = new UsernamePasswordCredentialsProvider(USER, PWD);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					commitAndPush(LOCAL_REPOSITORY, df.format(new Date()));
				}catch(Exception exception){
					exception.printStackTrace();
				}finally {
					frame.setVisible(false);
					System.exit(0);
				}
			}
		});
		frame.setVisible(true);
	}

	public static String clone(String url, String localPath) {
		try {
			CloneCommand cloneCommand = Git.cloneRepository().setURI(url);
			cloneCommand.setCredentialsProvider(CREDENTIAL);
			cloneCommand.setDirectory(new File(localPath)).call();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public static String add(String localPath, String filepattern) {
		try {
			Git git = Git.open(new File(localPath));
			AddCommand addCommand = git.add();
			addCommand.addFilepattern(filepattern);
			addCommand.call();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public static String rm(String localPath, String filepattern) {
		try {
			Git git = Git.open(new File(localPath));
			RmCommand rmCommand = git.rm();
			rmCommand.addFilepattern(filepattern);
			rmCommand.call();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public static String commit(String localPath, String message) {
		try {
			Git git = Git.open(new File(localPath));
			CommitCommand commitCommand = git.commit();
			commitCommand.setMessage(message);
			commitCommand.call();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public static String commitAndPush(String localPath, String message) {
		try {
			if (!new File(LOCAL_REPOSITORY).exists()) {
				clone(REMOTE_REPOSITORY, LOCAL_REPOSITORY);
			}
			List<String> fileTree = com.example.utils.FileUtils.getFileTree(LOCAL_REPOSITORY);
			fileTree = fileTree.stream().map(f -> f.substring(LOCAL_REPOSITORY.length(), f.length()))
					.filter(f -> !f.startsWith(POINT_GIT)).collect(Collectors.toList());
			// 我新增的
			fileTree.forEach(f -> add(LOCAL_REPOSITORY, f));
			Map<String, Set<String>> changedFiles = getChangedFiles(LOCAL_REPOSITORY);
			// 我直接删除的
			Set<String> missingFilesSet = changedFiles.get(MISSING);
			for (Iterator<String> iterator = missingFilesSet.iterator(); iterator.hasNext();) {
				String fileName = iterator.next();
				rm(LOCAL_REPOSITORY, fileName);
			}
			Set<String> changedFilesSet = changedFiles.get(CHANGED);
			for (Iterator<String> iterator = changedFilesSet.iterator(); iterator.hasNext();) {
				String fileName = iterator.next();
				add(LOCAL_REPOSITORY, fileName);
			}
			commit(LOCAL_REPOSITORY, message);
			push(LOCAL_REPOSITORY);

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public static String push(String localPath) {
		try {
			Git git = Git.open(new File(localPath));
			PushCommand pushCommand = git.push();
			pushCommand.setCredentialsProvider(CREDENTIAL);
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

	/**
	 * <pre>
	 * 记录上一次提交后做出的改变<br>
	 * commit 以后，手动删除文件，会记录为 missing 状态<br>
	 * commit 以后，修改文件，会记录为 modified 状态<br>
	 * commit 以后，调用 git rm filename 会记录为 removed 状态<br>
	 * </pre>
	 * 
	 * @param localPath
	 * @return
	 */
	public static Map<String, Set<String>> getChangedFiles(String localPath) {
		try {
			Map<String, Set<String>> result = new HashMap<String, Set<String>>();
			Git git = Git.open(new File(localPath));
			StatusCommand statusCommand = git.status();
			Status status = statusCommand.call();

			Set<String> added = status.getAdded();
			result.put(ADDED, added);

			Set<String> changed = status.getChanged();
			result.put(CHANGED, changed);

			Set<String> missing = status.getMissing();
			result.put(MISSING, missing);

			Set<String> removed = status.getRemoved();
			result.put(REMOVED, removed);

			Set<String> modified = status.getModified();
			result.put(MODIFIED, modified);

			System.err.println(JSON.toJSONString(result, true));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

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
	 * @return 错误信息
	 */
	public static String switchBranch(String localPath, String branch) {
		try {
			Git git = Git.open(new File(localPath));
			String newBranch = branch.substring(branch.lastIndexOf("/") + 1, branch.length());
			CheckoutCommand checkoutCommand = git.checkout();
			List<String> list = getLocalBranchNames(localPath);
			if (!list.contains(newBranch)) {
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
	 * 拿到当前本地分支名
	 * 
	 * @param localPath 主仓
	 * @return 当前本地分支名
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
	 * @return 当前远程分支名
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
	 * @return 所有远程
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
	 * @return 本地所有分支名
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
	 * @return 所有远程分支
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
				result.add(str);
				System.err.println(str);
			}
		}
		return result;
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
