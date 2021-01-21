//
//  GoodsList.swift
//  HJJSearchThird
//
//  Created by haojiajia on 2021/1/18.
//

import SwiftUI

struct GoodsList: View {
    
    @EnvironmentObject var store: Store
    @State private var searchText: String = ""

    var appStateBinding: Binding<AppState> {
        $store.appState
    }
    
    var appState: AppState {
        store.appState
    }
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    @GestureState private var dragOffset = CGSize.zero
    
    var body: some View {
        ZStack{
            Color("background_gray")
                .ignoresSafeArea()
            VStack {
                SearchBar(hideImage: .constant(false), text: appStateBinding.goodsChecker.searchText)
                    .padding([.leading,.top,.trailing], 10)
                if appState.isLoadingGoods {
                    hudView
                    Spacer()
                }else if (appState.filterGoods.count > 0) {
                    goodsListView
                }else {
                    noResultView
                }
            }
        }
        .navigationBarHidden(true)
        .onAppear {
            self.store.executeAction(action: .loadGoods)
        }
        .alert(item: appStateBinding.loadGoodsError) { error in
            Alert(title: Text(error.localizedDescription))
        }
        .onTapGesture {
            UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
        }
        .gesture(DragGesture().updating($dragOffset, body: { (value, state, transaction) in
            if (value.startLocation.x<20 && value.translation.width > 100) {
                appState.goodsChecker.searchText = ""
                self.presentationMode.wrappedValue.dismiss()
            }
        }))
    }
    
}

//MARK: subview
extension GoodsList {
    var hudView: some View {
        ZStack(alignment: .center) {
            VStack {
                Text("Loading ...")
                    .foregroundColor(.secondary)
                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle())
            }
            .frame(width: 120.0, height: 120.0)
            .background(Color(.systemGray5))
            .cornerRadius(16)
        }
        .padding(.top, UIScreen.main.bounds.size.height/3.5)
    }
    
    var noResultView: some View {
        VStack {
            Text("No result")
                .foregroundColor(.gray)
                .padding(.top, 50)
            Spacer()
        }
    }
    
    var goodsListView: some View {
        ScrollView {
            LazyVStack {
                ForEach(appState.filterGoods) { goods in
                    HStack {
                        Text(goods.category)
                            .font(.subheadline)
                            .foregroundColor(.gray)
                            .padding(.leading, 20)
                            .padding([.top, .bottom], 5)
                        Spacer()
                    }
                    ForEach(goods.items) { goodsDesc in
                        GoodsRow(goodsDesc: goodsDesc)
                    }
                }
            }
            
        }
        .background(Color("background_gray"))
    }

}

struct GoodsList_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ContentView()
            ContentView().previewDevice("iPhone SE (2nd generation)")
        }
    }
}


