var vm = new Vue({
    el: '#main-container',
    data:function(){
        return{
            user:'',
            article:{
                title:'',
                content:'',
                publishDate:'',
                publishUsername:'',
                userId:''
            }
        }

    },
    methods: {

        findPublishUser: function () {
            axios({url: path + "/article/findPublishUser"})
                .then(response => {
                    this.user = response.data;
                    this.article.userId=this.user.id;
                    this.article.publishUsername=this.user.username;
                    this.article.publishDate=this.dataNow();
                    console.log(this.article);
                }).catch(function (error) {
                console.log(error)
            });
        },
        add:function () {
            console.log("add");
            console.log(this.article);
            axios({url:path+"/article/add",method:'post',data:this.article})
                .then(response => {
                    console.log(response.data)
                    alert(response.data.msg);
                    if (response.data.flag){
                        //跳转
                        window.location.href=path+'/article/toList';
                    }
                }).catch(function(error){console.log(error)});
        },
        dataNow:function () {
            const dateTime = new Date();
            const YY = dateTime.getFullYear();
            const MM =
                dateTime.getMonth() + 1 < 10
                    ? '0' + (dateTime.getMonth() + 1)
                    : dateTime.getMonth() + 1;
            const D =
                dateTime.getDate() < 10 ? '0' + dateTime.getDate() : dateTime.getDate();

            return `${YY}-${MM}-${D}`;
        }

    }
    ,
    created: function () {
        this.findPublishUser();
    }
});