//
//  LoadView.swift
//  AuditionDemo
//
//  Created by dian bao on 2022/7/28.
//

import SwiftUI

//MARK: -加载View
struct LoadView: View {
    //MARK: - 属性
    @ObservedObject
    private var plManager:PageLoaderManager = PageLoaderManager.sharedInstance                  //分页加载器
    //MARK: - 方法
    var body: some View {
        HStack{
            if( plManager.LoadingFinish == false )
            {
                ProgressView("")
                    .progressViewStyle(.circular)
                    .padding(.top,12)
                    .padding(.trailing,5)
                Text("Loading...")
                    .font(.system(size: 24))
                    .foregroundColor(Color("#aeaeb2".uicolor()))
            }
            else
            {
                Text("No more data.")
                    .font(.system(size: 24))
                    .foregroundColor(Color("#aeaeb2".uicolor()))
            }
        }//: HStack
        .padding()
        .onAppear(){
            //这里做延时操作可以在性能好的手机上更容易看到分页加载图标动画
            DispatchQueue.global().asyncAfter(deadline:DispatchTime.now()+(Util.sharedInstance.forSimulation() == true ? 1.5:0)){
                //此视图一旦出面就要开始加载分页数据了
                plManager.LoadPageData()
            }
        }
    }
}

//MARK: - Preview
struct LoadView_Previews: PreviewProvider {
    static var previews: some View {
        LoadView()
    }
}
