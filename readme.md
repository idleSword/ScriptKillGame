# SkriptKillGame
### 游戏功能
本游戏是基于安卓开发的一款手机游戏，主要面向大学校园的学生等用户，通过在校园部署无线蓝牙低功耗信标，将剧本杀、Among us等包含一定场景的游戏与校园结合，使校园成为游戏场景，以此给游戏加入线下的交互功能。

#### 校园剧本杀游戏流程
1. 玩家创建/进入房间
2. 玩家选择剧本
3. 开始游戏
4. 玩家被分配/选择剧本角色后，获得角色剧本，阅读剧本
5. 玩家自由行动，在校园内搜寻线索
	- 玩家点击搜索按钮，扫描发现距离最近的线索。
	- 玩家靠近线索时（与线索距离在一定范围内），获得“附近存在线索”的提示。
	- 玩家打开扫描仪（相机），在镜头内扫描虚拟的线索
	- 玩家点击线索，加入已获取线索（部分一次性线索将消失，玩家也可选择将一些可销毁线索销毁，消失的线索无法被下一位玩家发现）。玩家可以选择是否公开线索（部分线索必须公开）。
	- 玩家选择发起紧急会议，每位玩家仅有有限数量次的发起权力。
6. 每隔一段时间，系统提示玩家聚集开会，进行讨论与投票，被投出玩家将无法继续游戏，进入上帝视角。
7. 有限轮数后，游戏结束，玩家聚集进行复盘。

### 环境
目前本游戏在安卓环境中运行

### 目录结构（部分）
```
.
├─readme.html 		//帮助文档
├─readme.md 		//帮助文档
├─app 				//应用
│  ├─keyStores		//密钥库
│  │      SchoolMap_key_store.jks	//发布版密钥（用于百度地图key）
│  │      
│  ├─libs
│  │  │  BaiduLBS_Android.jar				//百度地图api
│  │  │  mysql-connector-java-5.1.46.jar	//mysql驱动
│  │  │  postgresql-42.2.24.jar				//postgresql驱动
│  └─src 			//源文件                     
│      ├─main
│      │  │  AndroidManifest.xml
│      │  │  
│      │  ├─java
│      │  │  ├─com
│      │  │  │  └─example
│      │  │  │      └─test1
│      │  │  │              AmusSceneChooseActivity.java
│      │  │  │              MainActivity.java
│      │  │  │              MapApplication.java
│      │  │  │              ModeChooseActivity.java
│      │  │  │              PlayerActivity.java
│      │  │  │              RegisterActivity.java
│      │  │  │              RoomActivity.java
│      │  │  │              ScriptChooseActivity.java
│      │  │  │              
│      │  │  ├─model
│      │  │  │      Beacon.java
│      │  │  │      BeaconData.java
│      │  │  │      Clue.java
│      │  │  │      MyLocationListener.java
│      │  │  │      Player.java
│      │  │  │      SchoolMap.java
│      │  │  │      Script.java
│      │  │  │      SqlUtil.java
│      │  │  │      
│      │  │  └─service
│      │  │          AmusManager.java
│      │  │          Manager.java
│      │  │          PhotoProcess.java
│      │  │          ScriptKillManager.java
│      │  │          
│      │  └─res
│      │      ├─anim	//动画效果文件
│      │      │      scan_rorate.xml
│      │      │      
│      │      ├─drawable 	//图形文件即部分按钮风格文件
│      │      │      button_custom.xml
│      │      │      camera.png
│      │      │      camerabtn_custom.xml
│      │      │      camerapre.png
│      │      │      circle.png
│      │      │      clue.png
│      │      │      cluebtn_custom.xml
│      │      │      cluepre.png
│      │      │      fangdajing.png
│      │      │      fdjp.png
│      │      │      ic_launcher_background.xml
│      │      │      locate_me.png
│      │      │      locate_me_36.png
│      │      │      locate_me_36_grey.png
│      │      │      locate_me_36_pressed.png
│      │      │      locate_me_72.png
│      │      │      outcircle.png
│      │      │      script.png
│      │      │      scriptbtn_custom.xml
│      │      │      scriptpre.png
│      │      │      searchbtn_custom.xml
│      │      │      
│      │      ├─drawable-v24
│      │      │      ic_launcher_foreground.xml
│      │      │      
│      │      ├─layout 		//界面文件
│      │      │      activity_amus_scene_choose.xml
│      │      │      activity_main.xml
│      │      │      activity_mode_choose.xml
│      │      │      activity_player.xml
│      │      │      activity_register.xml
│      │      │      activity_room.xml
│      │      │      activity_script_choose.xml
│      │      │      
│      │      ├─mipmap-anydpi-v26
│      │      │      background.png
│      │      │      circle.png
│      │      │      ic_launcher.xml
│      │      │      ic_launcher_round.xml
│      │      │      outcircle.png
│      │      │      
│      │      ├─mipmap-hdpi
│      │      │      ic_launcher.webp
│      │      │      ic_launcher_round.webp
│      │      │      
│      │      ├─mipmap-mdpi
│      │      │      ic_launcher.webp
│      │      │      ic_launcher_round.webp
│      │      │      
│      │      ├─mipmap-xhdpi
│      │      │      ic_launcher.webp
│      │      │      ic_launcher_round.webp
│      │      │      
│      │      ├─mipmap-xxhdpi
│      │      │      ic_launcher.webp
│      │      │      ic_launcher_round.webp
│      │      │      
│      │      ├─mipmap-xxxhdpi
│      │      │      ic_launcher.webp
│      │      │      ic_launcher_round.webp
│      │      │      
│      │      ├─values
│      │      │      colors.xml
│      │      │      strings.xml
│      │      │      themes.xml
│      │      │      
│      │      ├─values-night
│      │      │      themes.xml
│      │      │      
│      │      └─xml
│      │              button_custom.xml
```

### 解决方案（主要为待完成、部分已解决）
- UI
	- [x] 地图显示
	- [x] 扫描线索动画
	- [x] 定位
	- [ ] 线索方向指示 
	- [ ] 靠近线索提示、开会按钮、系统提示、按钮设计完善
- 数据库
	- [x] 数据库连接
	- [ ] 数据库获取剧本数据并转为实体类
	- [ ] 数据库建表
	- [ ] 剧本撰写
- 游戏流程控制（Manager类，部署于服务器）
	- [ ] 玩家与Manager的通信，包括会议通知，投票，线索销毁等信息
	- [ ] 系统控制游戏轮数与胜利条件判断，发送开会、线索公开等系统消息
- 游戏业务逻辑
	- [x] 信标（线索）扫描
	- [ ] 信标扫描优化（按UUID或major等筛选扫描）
	- [ ] 获取信标（线索）方向
	- [ ] 连接信标获取信息
	- [ ] 靠近线索提示
	- [ ] 游戏开始后的角色剧本等初始化类
 	- [ ] 获取线索
 	- [ ] 阅读剧本
 	- [ ] 查看目前已有线索
 	- [ ] 查看系统信息
 	- [ ] 相机扫描线索
- 打开相机后的图像处理（两方案可单独或混合使用）
	1. 方案一
	- [ ] 将虚拟的线索图片与现实图片混合
	- [ ] 点击线索弹出选项框（包括获取、获取并销毁、解密等，销毁、解密等选项视具体为什么线索而定）
	- [ ] 获取、销毁等事件的线索图像变化
	2. 方案二
	- [ ] 靠近线索时，系统提示，给出一张线索场景图片
	- [ ] 相机扫描，进行场景匹配
	- [ ] 按下按钮拍照并进行图像匹配，或者间隔事件抓拍并进行匹配
- 设置信标
	- [ ] 单独做一个建议程序连接信标并设置参数
- 出局后上帝视角
	- [ ] 在地图上显示玩家与线索
	- [ ] 阅读全局剧本（复盘中也有）
	- [ ] 复盘游戏流程（事件事件流展示，复盘过程也有）

上述解决方案待补充、扩展、优化........
