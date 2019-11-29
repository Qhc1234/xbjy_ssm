var vm = new Vue({
    el:'.main-container',
    data:{
        user:{
            username:'',
            password:'',
            remember:''
        },
        register:{
            email:'',
            username:'',
            password:'',
            confirmPassword:''
        },
        forgot:{
            email:'',
            code:'',
            newPassword:''
        }
    },
    methods:{
        login:function(){
            axios({
                url:path+'/user/doLogin',params:this.user}).then( response => {
                console.log(response.data);
                //1.登录成功
                if(response.data.flag){
                    let user=response.data.user;
                    sessionStorage.setItem("user",JSON.stringify(user));
                    //跳转到index页面
                    location.href=path+"/user/toIndex";
                    //将用户状态保持，在其他页面能够显示用户名信息
                }else{
                    //2.失败则提示
                    alert(response.data.msg);
                }
            }).catch(function(error){
                alert(error);
            })
        },
        doRegister:function () {
            console.log("doRegister");
            if (this.register.email=='') {
                alert("请填写邮箱");
                return;
            }
            if (this.register.username=='') {
                alert("请填写用户名");
                return;
            }
            if (this.register.password=='') {
                alert("请填写密码");
                return;
            }
            if (this.register.password!=this.register.confirmPassword) {
                alert("两次输入的密码不一致");
                this.register.password='';
                this.register.confirmPassword='';
            }else {
                axios({url:path+"/user/doRegister", method: 'post',data:this.register})
                    .then(response => {
                        if(response.data.flag){
                            this.register.email='';
                            this.register.username='';
                            this.register.password='';
                            this.register.confirmPassword='';
                        }
                        alert(response.data.msg);
                    }).catch(function(error){console.log(error)});
            }
        },
        sendEmail:function(){
            axios({url:path+"/user/sendEmail",params:{"email":this.forgot.email}})
                .then(response => {
                    alert(response.data.msg);
                }).catch(function(error){console.log(error)});
        },
        changePassword:function () {
            axios({url:path+"/user/changePassword",method: 'post',data:this.forgot})
                .then(response => {
                    if(response.data.flag){
                        this.forgot.email='';
                        this.forgot.code='';
                        this.forgot.newPassword='';
                    }
                    alert(response.data.msg);
                }).catch(function(error){console.log(error)});
        },
        clear:function(){//清空对象数据

        }

    },
    created:function(){

    }
});