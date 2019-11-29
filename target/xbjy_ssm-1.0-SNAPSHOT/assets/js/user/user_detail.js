var vm = new Vue({
    el: '#main-container',
    data:function(){
        return{
            id:'',
            user: '',
            registerTime:'',
            loginTime:'',
            deptList:'',
            focusCount:'',
            pic:''
        }

    },
    methods: {
        //获取url中的参数
        getUrlParam:function (name) {
            let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            let r = window.location.search.substr(1).match(reg);  //匹配目标参数
            if (r != null) return unescape(r[2]); return null; //返回参数值
        },
        findUserinfoById:function () {
            this.id=this.getUrlParam("id");
            console.log(this.id);
            axios({url:path+"/user/findById",params:{"id":this.id}})
                .then(response => {
                    this.user = response.data;
                    this.pic='/uploads/'+this.user.pic;
                    this.registerTime=this.formatDate(this.user.register_time);
                    this.loginTime=this.formatDate(this.user.login_time);
                    if(this.user.is_secret=="0"){
                        this.user.is_secret=false;
                    }
                    else {
                        this.user.is_secret=true;
                    }
                }).catch(function(error){console.log(error)});
        },
        selectDept:function () {
            console.log("selectDept");
            axios({url:path+"/dept/selectAll"})
                .then(response => {
                    this.deptList = response.data;
                    console.log(this.deptList);
                }).catch(function(error){console.log(error)});
        },
        update:function () {
            axios({url:path+"/user/update",method:'post',data:this.user})
                .then(response => {
                    if(response.data.flag){
                        //跳转到list页面
                        location.href=path+"/user/toList";
                    }else{
                        //2.失败则提示
                        alert(response.data.msg);
                    }
                    console.log(this.user);
                }).catch(function(error){console.log(error)});
        },
        //查询关注数
        findFocusCount:function () {
            this.id=this.getUrlParam("id");
            axios({url:path+"/user/findFocusCount",params:{"id":this.id}})
                .then(response => {
                    this.focusCount=response.data;
                    console.log(this.focusCount);
                }).catch(function(error){console.log(error)});
        },
        addLookCount:function () {
            this.id=this.getUrlParam("id");
            axios({url:path+"/user/addLookCount",params:{"id":this.id}})
                .then(response => {
                }).catch(function(error){console.log(error)});
        },
        formatDate:function (date) {
            if (date){
                // 格式化时间
                const dateTime = new Date(date);
                const YY = dateTime.getFullYear();
                const MM =
                    dateTime.getMonth() + 1 < 10
                        ? '0' + (dateTime.getMonth() + 1)
                        : dateTime.getMonth() + 1;
                const D =
                    dateTime.getDate() < 10 ? '0' + dateTime.getDate() : dateTime.getDate();

                return `${YY}-${MM}-${D}`;
            } else {
                return '';
            }
        }
    },
    created: function () {
        this.selectDept();
        this.addLookCount();
        this.findUserinfoById();
        this.findFocusCount();

    }
});