var vm = new Vue({
    el: '#main-container',
    data:function(){
        return{
            id:'',
            article:'',
            userList:'',
            goodCount:'',
            favoriteCount:'',
            focusUserList:'',
            text:'',
            isGood:'',
            isFavorite:''
        }

    },
    methods: {
        //获取url中的参数
        getUrlParam:function (name) {
            let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            let r = window.location.search.substr(1).match(reg);  //匹配目标参数
            if (r != null) return unescape(r[2]); return null; //返回参数值
        },
        findArticleById:function () {
            this.id=this.getUrlParam("id");
            console.log(this.id);
            axios({url:path+"/article/findById",params:{"id":this.id}})
                .then(response => {
                    this.article = response.data;
                    console.log(this.article);
                }).catch(function(error){console.log(error)});
        },
        findGoodUsers:function () {
            this.id=this.getUrlParam("id");
            console.log(this.id);
            axios({url:path+"/article/findGoodUsers",params:{"id":this.id}})
                .then(response => {
                    this.userList=response.data.users;
                    this.goodCount=response.data.goodCount;
                    for (let i=0;i<this.userList.length;i++){
                        this.userList[i].pic='/uploads/'+this.userList[i].pic;
                    }
                    console.log(this.userList);
                    console.log(this.goodCount);
                }).catch(function(error){console.log(error)});
        },
        addBrowseCount:function () {
            this.id=this.getUrlParam("id");
            axios({url:path+"/article/addBrowseCount",params:{"id":this.id}})
                .then(response => {
                }).catch(function(error){console.log(error)});
        },
        findUsersFocusFavorite:function () {
            this.id=this.getUrlParam("id");
            axios({url:path+"/article/findUsersFocusFavorite",params:{"id":this.id}})
                .then(response => {
                    this.focusUserList=response.data;
                    console.log(this.focusUserList);
                    if (this.focusUserList.length>0){
                        let textTemp='您关注的：';
                        for (let i=0;i<this.focusUserList.length;i++){
                            textTemp+=this.focusUserList[i].real_name+'、';
                        }
                        textTemp=textTemp.substring(0,textTemp.length-1);
                        textTemp+='等人收藏了该文章';
                        this.text=textTemp;
                    } else {
                        this.text='您关注的用户，无人收藏了该文章';
                    }


                }).catch(function(error){console.log(error)});
        },
        findFavoriteCount:function () {
            this.id=this.getUrlParam("id");
            axios({url:path+"/article/findFavoriteCount",params:{"id":this.id}})
                .then(response => {
                    this.favoriteCount=response.data;
                    console.log(this.favoriteCount);
                }).catch(function(error){console.log(error)});
        },
        findIsGood:function () {
            this.id=this.getUrlParam("id");
            axios({url:path+"/article/findIsGood",params:{"id":this.id}})
                .then(response => {
                    this.isGood=response.data;
                    console.log(this.isGood);
                }).catch(function(error){console.log(error)});
        },
        findIsFavorite:function () {
            this.id=this.getUrlParam("id");
            axios({url:path+"/article/findIsFavorite",params:{"id":this.id}})
                .then(response => {
                    this.isFavorite=response.data;
                    console.log(this.isFavorite);
                }).catch(function(error){console.log(error)});
        },
        favorite:function () {
            axios({url:path+"/article/favorite",params:{"id":this.id,"flag":this.isFavorite}})
                .then(response => {
                    if (response.data.flag) {
                        this.findFavoriteCount();
                        this.findUsersFocusFavorite();
                        this.isFavorite=(!this.isFavorite);
                    }
                    alert(response.data.msg);
                }).catch(function(error){console.log(error)});
        },
        good:function () {
            axios({url:path+"/article/good",params:{"id":this.id,"flag":this.isGood}})
                .then(response => {
                    if (response.data.flag) {
                        this.findGoodUsers();
                        this.findUsersFocusFavorite();
                        this.isGood=(!this.isGood);
                    }
                    alert(response.data.msg);
                }).catch(function(error){console.log(error)});
        },
    },
    created: function () {
        this.findArticleById();
        this.findGoodUsers();
        this.addBrowseCount();
        this.findUsersFocusFavorite();
        this.findFavoriteCount();
        this.findIsGood();
        this.findIsFavorite();
    }
});