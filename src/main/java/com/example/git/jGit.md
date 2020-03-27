底层命令
JGit 的 API 有两种基本的层次：底层命令和高层命令。 这个两个术语都来自 Git ，并且 JGit 也被按照相同的方式粗略地划分：高层 API 是一个面向普通用户级别功能的友好的前端（一系列普通用户使用 Git 命令行工具时可能用到的东西），底层 API 则直接作用于低级的仓库对象。

大多数 JGit 会话会以 Repository 类作为起点，你首先要做的事就是创建一个它的实例。 对于一个基于文件系统的仓库来说（嗯， JGit 允许其它的存储模型），用 FileRepositoryBuilder 完成它。

```java
// 创建一个新仓库
Repository newlyCreatedRepo = FileRepositoryBuilder.create(
    new File("/tmp/new_repo/.git"));
newlyCreatedRepo.create();

// 打开一个存在的仓库
Repository existingRepo = new FileRepositoryBuilder()
    .setGitDir(new File("my_repo/.git"))
    .build();
```
无论你的程序是否知道仓库的确切位置，builder 中的那个流畅的 API 都可以提供给它寻找仓库所需所有信息。 它可以使用环境变量 （.readEnvironment()） ，从工作目录的某处开始并搜索 （.setWorkTree(…).findGitDir()） , 或者仅仅只是像上面那样打开一个已知的 .git 目录。

当你拥有一个 Repository 实例后，你就能对它做各种各样的事。 下面是一个速览：


```java
// 获取引用
Ref master = repo.getRef("master");

// 获取该引用所指向的对象
ObjectId masterTip = master.getObjectId();

// Rev-parse
ObjectId obj = repo.resolve("HEAD^{tree}");

// 装载对象原始内容
ObjectLoader loader = repo.open(masterTip);
loader.copyTo(System.out);

// 创建分支
RefUpdate createBranch1 = repo.updateRef("refs/heads/branch1");
createBranch1.setNewObjectId(masterTip);
createBranch1.update();

// 删除分支
RefUpdate deleteBranch1 = repo.updateRef("refs/heads/branch1");
deleteBranch1.setForceUpdate(true);
deleteBranch1.delete();

// 配置
Config cfg = repo.getConfig();
String name = cfg.getString("user", null, "name");
```

这里完成了一大堆事情，所以我们还是一次理解一段的好。

第一行获取一个指向 master 引用的指针。 JGit 自动抓取位于 refs/heads/master 的 真正的 master 引用，并返回一个允许你获取该引用的信息的对象。 你可以获取它的名字 （.getName()） ，或者一个直接引用的目标对象 （.getObjectId()） ，或者一个指向该引用的符号指针 （.getTarget()） 。 引用对象也经常被用来表示标签的引用和对象，所以你可以询问某个标签是否被“削除”了，或者说它指向一个标签对象的（也许很长的）字符串的最终目标。

第二行获得以 master 引用的目标，它返回一个 ObjectId 实例。 不管是否存在于一个 Git 对象的数据库，ObjectId 都会代表一个对象的 SHA-1 哈希。 第三行与此相似，但是它展示了 JGit 如何处理 rev-parse 语法（要了解更多，请看 分支引用 ），你可以传入任何 Git 了解的对象说明符，然后 JGit 会返回该对象的一个有效的 ObjectId ，或者 null 。

接下来两行展示了如何装载一个对象的原始内容。 在这个例子中，我们调用 ObjectLoader.copyTo() 直接向标准输出流输出对象的内容，除此之外 ObjectLoader 还带有读取对象的类型和长度并将它以字节数组返回的方法。 对于一个（ .isLarge() 返回 true 的）大的对象，你可以调用 .openStream() 来获得一个类似 InputStream 的对象，它可以在没有一次性将所有数据拉到内存的前提下读取对象的原始数据。

接下来几行展现了如何创建一个新的分支。 我们创建一个 RefUpdate 实例，配置一些参数，然后调用 .update() 来确认这个更改。 删除相同分支的代码就在这行下面。 记住必须先 .setForceUpdate(true) 才能让它工作，否则调用 .delete() 只会返回 REJECTED ，然后什么都没有发生。

最后一个例子展示了如何从 Git 配置文件中获取 user.name 的值。 这个 Config 实例使用我们先前打开的仓库做本地配置，但是它也会自动地检测并读取全局和系统的配置文件。

这只是底层 API 的冰山一角，另外还有许多可以使用的方法和类。 还有一个没有放在这里说明的，就是 JGit 是用异常机制来处理错误的。 JGit API 有时使用标准的 Java 异常（例如 IOException ），但是它也提供了大量 JGit 自己定义的异常类型（例如 NoRemoteRepositoryException、 CorruptObjectException 和 NoMergeBaseException）。

高层命令
底层 API 更加完善，但是有时将它们串起来以实现普通的目的非常困难，例如将一个文件添加到索引，或者创建一个新的提交。 为了解决这个问题， JGit 提供了一系列高层 API ，使用这些 API 的入口点就是 Git 类：

```java
Repository repo;
// 构建仓库……
Git git = new Git(repo);
```

Git 类有一系列非常好的 构建器 风格的高层方法，它可以用来构造一些复杂的行为。 我们来看一个例子——做一件类似 git ls-remote 的事。

```java
CredentialsProvider cp = new UsernamePasswordCredentialsProvider("username", "p4ssw0rd");
Collection<Ref> remoteRefs = git.lsRemote()
    .setCredentialsProvider(cp)
    .setRemote("origin")
    .setTags(true)
    .setHeads(false)
    .call();
for (Ref ref : remoteRefs) {
    System.out.println(ref.getName() + " -> " + ref.getObjectId().name());
}
```

这是一个 Git 类的公共样式，这个方法返回一个可以让你串连若干方法调用来设置参数的命令对象，当你调用 .call() 时它们就会被执行。 在这情况下，我们只是请求了 origin 远程的标签，而不是头部。 还要注意用于验证的 CredentialsProvider 对象的使用。

在 Git 类中还可以使用许多其它的命令，包括但不限于 add、blame、commit、clean、push、rebase、revert 和 reset。