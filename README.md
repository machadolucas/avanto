<h1>Avanto</h1>
<p>by Lucas Machado</p>

<h2>Warning!</h2>
<p>Although this platform was being developed for over an year, just recently I decided to made it available publicly. Therefore, I didn't tested it in non specific contexts yet. It was made to run in a JBoss AS7 in Openshift, but I know it also runs in Apache Tomcat 7. It needs a MongoDB database, but I will make this abstract in the future.</p>

<h2>Blog system</h2>
<h3>What</h3>
<p>Avanto is a blog system developed in Java (with JSF, Primefaces and some other technologies).</p>
<h3>Why Avanto</h3>
<p>I like ice swimming after a good sauna ;)</p>

<h2>Features</h2>
<p>The blog system provides an quite comprehensive administrative interface accessible in the address "/admin". After initializing the application for the first time, the first attempt of login will be recorded as the administrative account. (You could change the data later).<p/>
<p>Passwords are correctly cryptographed. ;)<p/>
<p>It is fully compliant to receive modern code like HTML5, CSS3 and so on.<p/>
<p>The blog system provides a RSS feed of the posts in the address "/rss"<p/>

<h2>Requirements and instructions</h2>
<ul>
	<li>The code is currently available as an Eclipse project. You will also need Maven to build it.</li>
	<li>The project is currently configured to Java 7.</li>
	<li><strong>MongoDB</strong>: You need a MongoDB instance running. You can configurate the connection settings in the "Database.properties" file that is in the package me.machadolucas.avanto.db</li>
	<li><strong>Internationalization</strong>: The system is compatible with internationalization. The strings can be configured in the locale correspondent files in the package me.machadolucas.avanto.resources. After that, they could be invoked in the xhtml files by using JSF Expression Language and the variable msg ("{#msg.key}" renders the value associated to 'key').</li>
</ul>