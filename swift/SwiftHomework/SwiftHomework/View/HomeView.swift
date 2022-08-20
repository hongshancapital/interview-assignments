//
//  HomeView.swift
//  SwiftHomework
//
//  Created by ljx on 2022/8/14.
//

import SwiftUI

struct HomeView: View {
    @StateObject var vm = ViewModel()
    
    init(){
        
    }
    
    var body: some View {
        NavigationView{
            ZStack {
                RefreshableScrollView.init { done in
                    DispatchQueue.main.asyncAfter(deadline: .now() + 0.8) {
                        vm.refreshData()
                        done()
                    }
                } onLoadMore: { done in
                    DispatchQueue.main.asyncAfter(deadline: .now() + 0.8) {
                        vm.loadMore()
                        done()
                    }
                } refreshProgress: { state in
                    RefreshActivityIndicator(isAnimating: state == .topLoading) {
                        $0.hidesWhenStopped = false
                    }
                } loadMoreProgress: { state in
                    if(vm.isNoMoreData){
                        Text("No more data.")
                    }else{
                        RefreshActivityIndicator(isAnimating: state == .topLoading) {
                            $0.hidesWhenStopped = false
                        }
                    }
                } content: {
                    VStack{
                        ForEach(vm.currentList.indices , id: \.self){ i in
                            AppInfoCellView(appDetailData: vm.currentList[i])
                        }
                    }
                }.background(Color.init(white: 0.95))
                if(vm.allAppList == nil){
                    ProgressView().fixedSize()
                }
            }
        .navigationTitle("App").background(Color.init(white: 0.95))
        }.navigationViewStyle(StackNavigationViewStyle())
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
