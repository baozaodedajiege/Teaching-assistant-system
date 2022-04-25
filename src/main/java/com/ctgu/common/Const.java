package com.ctgu.common;

public class Const {

    // Session
    public final static String CURRENT_ACCOUNT = "current_account";

    //本地服务器-文件,图片所在位置,图片上传位置
//    public static final String UPLOAD_FILE_PATH = "file:D:/IdeaProjects/data/springboot-penguin/upload/";
//    public static final String UPLOAD_FILE_IMAGE_PATH = "D:/IdeaProjects/data/springboot-penguin/upload/images/";

    //云服务器-文件,图片所在位置,图片上传位置
    public static final String UPLOAD_FILE_PATH = "file:/root/obeSystem/data/upload/";
    public static final String UPLOAD_FILE_IMAGE_PATH = "/root/obeSystem/data/upload/images/";
    public static final String UPLOAD_FILE_DOC_PATH = "/root/obeSystem/data/upload/doc/";
    public static final String UPLOAD_FILE_HTML_PATH = "/root/obeSystem/data/upload/html/";

    //默认教学大纲地址
    public static final String DEFAULT_SYLLABUS_PATH = "/upload/html/";
    //默认上传教学大纲界面
    public static final String DEFAULT_FILE_HTML_PATH = "/html/init.html";

    //默认头像url
    public static final String DEFAULT_AVATAR_IMG_URL = "headimg_placeholder.png";
    //课程默认图片
    public static final String DEFAULT_SUBJECT_IMG_URL = "problemset_default.jpg";

    public static final int subjectPageSize = 15;
    public static final int contestPageSize = 10;
    public static final int questionPageSize = 10;
    public static final int accountPageSize = 15;
    public static final int postPageSize = 8;
    public static final int gradePageSize = 12;
    public static final int commentPageSize = 15;
    public static final int classPageSize = 15;
    public static final int questionnairePageSize = 15;

    public static final int StudentLevel = 0;
    public static final int TeacherAssistant = 1;
    public static final int TeacherLevel = 2;
    public static final int AdminLevel = 3;

    //MD5加盐
    public static final String MD5_SALT = "tree";
    //分页数据
    public final static String DATA = "data";

    public final static String SPLIT_CHAR = "_~_";
    // redis过期时间（小时）
    public final static int REDIS_OUT_TIME = 1;

}
