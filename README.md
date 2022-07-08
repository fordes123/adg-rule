<div align="center">
<h1>AdGuard Rule</h1>
  <p>
    一个简易的Java程序，用于合并与更新AdGuardHome过滤规则
  </p>
<!-- Badges -->
<p>
  <a href="#">
    <img src="https://img.shields.io/github/last-commit/fordes123/adg-rule" alt="last update" />
  </a>
  <a href="#">
    <img src="https://img.shields.io/github/forks/fordes123/adg-rule" alt="forks" />
  </a>
  <a href="#">
    <img src="https://img.shields.io/github/stars/fordes123/adg-rule" alt="stars" />
  </a>
  <a href="https://github.com/fordes123/adg-rule/issues/">
    <img src="https://img.shields.io/github/issuesfordes123/adg-rule" alt="open issues" />
  </a>
  <a href="https://github.com/fordes123/adg-rule/LICENSE">
    <img src="https://img.shields.io/github/license/fordes123/adg-rule.svg" alt="license" />
  </a>
</p>

<h4>
    <a href="#a">项目说明</a>
  <span> · </span>
    <a href="#b">规则订阅</a>
  <span> · </span>
    <a href="#c">快速上手</a>
  <span> · </span>
    <a href="#d">问题反馈</a>
  </h4>
</div>

<br />

<h2 id="a">📔 项目说明</h2>

本项目旨在按需求整合AdGuardHome规则。定时从上游订阅获取规则，去除重复和不受支持的规则并进行分类。

#### 订阅规则

<details>
<summary>点击查看</summary>
<ul>
    <li><a href="https://github.com/hoshsadiq/adblock-nocoin-list/">adblock-nocoin-list</a></li>
    <li><a href="https://github.com/durablenapkin/scamblocklist">Scam Blocklist</a></li>
    <li><a href="https://someonewhocares.org/hosts/zero/hosts">Dan Pollock's List</a></li>
    <li><a href="https://cdn.jsdelivr.net/gh/AdguardTeam/FiltersRegistry/filters/filter_15_DnsFilter/filter.txt">AdGuard DNS filter</a></li>
    <li><a href="https://pgl.yoyo.org/adservers/serverlist.php?hostformat=adblockplus&showintro=1&mimetype=plaintext">Peter Lowe's List</a></li>
    <li><a href="https://abp.oisd.nl/basic/">OISD Blocklist Basic</a></li>
    <li><a href="https://adaway.org/hosts.txt">AdAway Default Blocklist</a></li>
    <li><a href="https://github.com/crazy-max/WindowsSpyBlocker">WindowsSpyBlocker</a></li>
    <li><a href="https://github.com/o0HalfLife0o/list">HalfLife（pc）</a></li>
    <li><a href="https://github.com/banbendalao/ADgk">Adgk</a></li>
    <li><a href="https://github.com/VeleSila/yhosts">yhosts</a></li>
    <li><a href="https://github.com/pboymt/Steam520">Steam Hosts</a></li>
    <li><a href="https://gitlab.com/ineo6/hosts">Github Hosts</a></li>
    <li><a href="https://onedrive-hosts.learningman.top/">OneDrive Hosts</a></li> 
</ul>
</details>

#### 本地规则

- [mylist](#)
主要是对上游规则的修正补充，根据日常使用体验，解除一些失误拦截

<h2 id="b">🎯 规则订阅</h2>

| 名称       | 说明                               | Github订阅   | jsDelivr加速订阅 |
|---------- |------------------------------------|------------|--------------|
| all.txt   | 仅去重的规则合集，适用于AdGuard客户端| [✈️点此查看]() | [🚀点此查看]()   |
| adgh.txt  | 针对AdGuardHome的规则              | [✈️点此查看]() | [🚀点此查看]()   |
| hosts.txt | hosts规则，包含一些访问加速         | [✈️点此查看]() | [🚀点此查看]()   |
| mylist    | 人工修正的补充规则，人工更新         | [✈️点此查看]() | [🚀点此查看]()   |

<h2 id="c">🛠️ 快速开始</h2>

- fork本项目
- 编辑`src/main/resources/application.yml`文件，在`rule.remote`节点下添加你的上游规则订阅链接，在`rule.local`节点下添加你的本地规则，注意本地规则文件应加入项目根目录`rule`文件夹
- 编辑`.github/workflows/auto-update.yml` 文件，将`Commit Changes`区块下邮箱与用户名更改为你的
- 提交所有修改并等待`Github Action`执行，执行完成后相应规则生成在`rule`目录下

<h2 id="d">💬 问题反馈</h2>

- 👉 [issues]()