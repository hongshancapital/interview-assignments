<!-- Content Header (Page header) -->
<section class="content-header">
    <h1>
        用户注册
        <small></small>
    </h1>
</section>
@if(count($errors)>0)
    <div class="box-body">
        <div class="alert alert-danger alert-dismissible">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
            <h4><i class="icon fa fa-ban"></i>错误：</h4>
            @foreach($errors->all() as $error)
                {{$error}}
            @endforeach
        </div>

    </div>
@endif
<!-- Main content -->
<section class="content">
    <div class="row">
        <!-- left column -->
        <div class="center-block col-md-12">
            <div class="box box-primary">

                <form action="/register" method="post" class="form-horizontal" enctype="multipart/form-data"
                      id="bookss">
                    <input type="hidden" name="_token" value="{{ csrf_token() }}">
                    <input type="hidden" name="type" value="1">
                    <div class="box-body">
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label">姓名：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="username"
                                       value="{{ old('username') }}" placeholder="姓名..">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label">密码：</label>
                            <div class="col-sm-4">
                                <input type="password" class="form-control" name="password" id="password"
                                       value="{{ old('password') }}" placeholder="密码..">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label">确认密码：</label>
                            <div class="col-sm-4">
                                <input type="text" id="repeat_password" onblur="checkPassword()" class="form-control" name="repeat_password"
                                       value="{{ old('repeat_password') }}">
                            </div>
                        </div>

                        <div class="box-footer">
                            <div class="col-sm-4 col-sm-offset-3">
                                <button type="submit" class="btn btn-block btn-social btn-facebook btn-flat">提交
                                </button>
                            </div>
                    </div>
                </form>

            </div>


        </div>

    </div>
    <!-- /.row -->
</section>
<!-- /.content -->

@endsection

@section('script')
    <script src="{{ asset('js/sweet-alert.min.js') }}" type="text/javascript"></script>
    <script src="{{ asset('js/layer/layer.js') }}" type="text/javascript"></script>
    <script language=javascript>
        function checkPassword() {
            var pasword = $("#pasword").val();
            var repeat_password = $("#repeat_password").val();
            if (pasword != repeat_password) {
                layer.alert('两次密码不一致！');
            }
        }
    </script>
@endsection
