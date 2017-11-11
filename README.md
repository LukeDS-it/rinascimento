# Rinascimento
Rinascimento is a Groovy based cms, that tries to be easy and fully customizable.

This readme covers the features of the product and the getting started. If you're interested
on more instructions about how to create extensions, templates, etc, please refer to the
wiki.

## Why?
You'll say "but there are plenty of CMS solutions on the internet, and they are all open
and well established".

Short answer is yes, they are, but none matches my idea of a CMS.

Why?

* **Technology**: A CMS manages data. Sometimes important data. The most common CMS are
  made in PHP, that is a language that in the field of IT is widely known as a poor choice
  for anything that must be solid (you can argue as much as you want, but even Facebook
  makes its core in C and then compiles it to php using their own tools).
* **Customisability**: Many good CMS exist out there (both PHP and not), and yes, they
  offer a good level of customisability. But if you want to get your hands dirty a little
  bit, for example making a new template, then you'll be off for a bad surprise, and
  unless you have great skills of PHP / Java / whatnot you'll be probably spending your
  free weekends on pages and pages and pages and pages [...] of php guides.
  The more customisable a CMS is, the more you'll have to work your way out of the mud
  when trying to extend it.
* **Focus on the content**: It doesn't matter how good the user interface is in
  any one of the afore(not)mentioned CMS. Each one of them has its own rules about
  how to insert new content, and they can be quite puzzling for any new user, even if
  they are somewhat tech-savvy. The most important thing in a website is its content,
  there's no doubt on it. The user wants to insert content and they need to be able to
  insert new content without having to worry about wiring `x` to `y`, adding a new voice in
  menu `z`, and then linking back menu `z` to super-menu `q`...
* **Internationalisation**: this is one of the most difficult points in the history
  of web-making. Every CMS has its own way to manage I18N, and it most often requires
  making copies of pages, more cross-linking, etc. As per the previous point, user should
  focus on the content, and, let's add this: to the translation of the content. No more.
* **Multi-tenant**: Ok, this is not strictly a user's concern, and chances are that
  even you, who reached this far in reading, do not know what I'm talking about.
  I'm giving a shout to all the system administrators that each time they need to create
  a new website for a new client need to copy a ton of installation files, do configuration
  and **remember** how to do it. Well, I hear you, I seriously do.

Please note that I, as a web developer, have faced all of the previous issues when
creating websites. So I thought that maybe, just maybe, with time and patience I could
create something that's easy to use, safe, customisable, content-centered and
multi-language. Oh and multi-tenant too. See next paragraph for the "what"

## What?
Enter Rinascimento. Rinascimento, *renaissance* in italian. The renaissance movement
started what known as the Modern age, with new cultural, technical and ideological ideas.

Rinascimento wants to be a symbol of this, for all web developers that are tired of
having to remember or study a ton of almost unintelligible documentation.

Here is **what** Rinascimento aims to offer:

* Content-focus: this is the first point that I addressed in my mind. What's the thing
  that users are likely to be most familiar with? The answer is: a file explorer.
  You create folders to make order in your files, and each folder contains other
  folders or files, for how many levels you decide.
  In my mind, websites can be structured in that way too. But I wanted it to be even more
  simple: category and pages? No, that's too much for anyone to bear, and a CMS must only
  M (manage) "C": Content. So each page can have "children", in this way you can
  structure a website starting from the sitemap. Cool, huh? So "index" can be a page
  with some bold nice text to introduce your site, and have links (thanks to
  a menu widget) to all the first-level children, which in turn can be just plain old pages
  or contain other children, to go in deeper detail

* Multilanguage: This should really go in the previous section, but it's so important
  that I decided to give it a separate point in the list. Each page you create can
  be "translated" in more languages without having to duplicate it: since the page is
  just a representation of what it contains, you can assign to each page one different
  content for each language, and the CMS will show the user just the language of its
  choosing (inferred by the operating system's setting). All elements in the page
  for which you provide multiple translations will be internationalised.
  
  If a user browses your website in a language that you didn't translate the site into,
  it will fall back to the default language that you can specify.

* Safe production-ready technology: Rinascimento is implemented in Groovy, which is
  a superset of Java, and Java itself. This means that everything that runs in the
  server is compiled beforehand to check for illegal operations. And it makes extension
  making more fun.

* Customisable: Here comes the difficult part (for me). I gave all myself to think
  about a way to give every newcomer the ability to customise the CMS without having
  to learn a new programming language, if they just wish to change the theme or the
  page template:
    * To create a new template you don't need to know Java
    * To create a new template you don't need to know HTML
    * To create a new template you don't need to know CSS (but you *may* want to.
      Or you can reuse resources from the internet)
     
  A page template can be created from the inside of the CMS, just by giving information
  of the various pieces that compose it. E.g. you can say that you need a row with
  three columns just by drawing them, and then you can style them assigning the css
  classes from external resources that you can either choose from the internet from
  official CDNs or make it yourself (if you know CSS) or have an expert do it for you.
  Then you can decide which kind of content will go in each container, by selecting
  a wide range of widgets, either the default ones that Rinascimento provides, or
  some custom extension that you may have downloaded / commissioned to programming
  expert.

* Multitenancy: you don't need to reinstall Rinascimento each time you need a new
  website. System administrator just need to point the DNS to the address of the
  server where Rinascimento is installed. The CMS will take care of using the
  correct configuration for the guest website, which can be either created via
  a control panel that allows quick initialisation of the needed directories,
  or can be manually configured for people who want everything under their control.
  
* Live refresh: This is a bonus that I like to add. All the CMS based on PHP have
  a major advantage: since they're all scripts and no compilation has been made, you
  can install and uninstall extensions, widgets and whatnot just by copying files
  (which is what an "installation" of a plugin really is in any one of them). 
  
  Any Java-based CMS suffers from the problem that, being all compiled code, the
  application needs to be restarted if the code-base changes, thus making anything
  that works with "extensions" unable to provide a smooth experience of plugin
  installation.
  
  Rinascimento, instead, since it works with Groovy, can compile at runtime just
  the small portions of the extensions, making it possible to install the extensions
  in a separate directory, eliminating the need of restarting the application on
  plugin installation, and avoiding to lose extension data when the CMS itself must
  be updated. This allows also third party companies to develop their own business
  logic with Rinascimento without having to recompile their own solution when
  important upgrades are released.

## When?
This is a bit more difficult to say. I work 5 days a week and I try to spend my free
time completing this software. Many times I don't have ideas on how to proceed with
it, other times I decide to completely rewrite entire parts of it in order to improve
speed, reliability or easiness of usage. Other times I don't work on it at all,
because I'm busy learning new stacks of technology that can be useful in my
carreer.

Currently I can say that the CMS is still in a embryonic stage, with just a couple
of things working, but they prove that my idea is not just utopia:

* Page rendering works fine
* Multitenancy works correctly
* Custom extensions can be loaded from external paths, without having to reload
  the applications when installing or updating them
* Basically, the content-serving system is ready, but you'll have to manually
configure the environment and do the SQL yourself to be able to use the CMS.

## Where?
As soon as they are available, you'll be able to download the latest releases of
Rinascimento on my website (which, by the way, will be powered by Rinascimento itself).

## Who?
I am [Luca Di Stefano](http://ldsoftware.it), a developer from Italy. I do this mainly for passion, and
not revenue. Even though, if you decide to help me out either by donating or
contributing code, I could be able to turn this project into the new
(famous CMS name here)!