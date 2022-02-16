//
//  ContentView.swift
//  Demo
//
//  Created by Kai on 2022/2/16.
//

import SwiftUI

struct ContentView: View {
        
    @EnvironmentObject private var dataManager: DataManager
    @State var list = [KKModel]()
    
    @State private var isNoMoreData: Bool = false
    
    private var pageCount = 20
    
    @State private var page = 0
    
    private func loadData() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            var count = page * pageCount + pageCount
            if count > dataManager.list.count {
                count = dataManager.list.count
                isNoMoreData = true
            } else {
                page += 1
            }
            list = Array(dataManager.list[0..<count])
        }
    }
    
    var body: some View {
        NavigationView {
            
            ZStack {
                ProgressView()
                    .navigationTitle("App").opacity(list.count == 0 ? 1 : 0)
                List {
                    ForEach(list) { model in
                        KKCell(model: model)
                            .cornerRadius(10)
                            .listRowSeparator(.hidden)
                            .listRowBackground(Color.clear)
                    }
                    KKLoadingView(isNoMoreData: isNoMoreData)
                        .listRowBackground(Color.clear)
                        .onAppear {
                            loadData()
                        }
                }
                .opacity(list.count == 0 ? 0 : 1)
                .listStyle(.grouped)
                .navigationBarTitle("App")
                .refreshable {
                    page = 0
                    isNoMoreData = false
                    loadData()
                }
            }
            
            
            
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}


struct KKCell: View {

    var model: KKModel
    @State var isSelect: Bool
    
    init(model: KKModel) {
        self.model = model
        isSelect = model.like
    }
    
    var body: some View {
        HStack() {
            AsyncImage(url: URL(string: model.artworkUrl60)) { image in
                image.resizable()
            } placeholder: {
                ProgressView()
            }
            .frame(width: 60, height: 60)
            .cornerRadius(10)
            
            VStack(alignment: .leading) {
                Text(model.trackCensoredName)
                    .bold()
                    
                Text(model.description)
                    .lineLimit(2)
                    .font(.system(size: 12))
            }
            
            Spacer()
            
            Button  {
                model.like.toggle()
                withAnimation {
                    isSelect.toggle()
                }
                
            } label: {
                let imgName = isSelect ? "suit.heart.fill" : "suit.heart"
                let color = isSelect ? UIColor(235, 88, 71) : UIColor(153, 153, 153)
                Image(uiImage: UIImage(systemName: imgName)!.xk_imageWithColor(color: color))
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 20, height: 20)
                    .scaleEffect(isSelect ? 1.1 : 1)
            }
            .buttonStyle(BorderlessButtonStyle())
        }
        .padding(10)
        .background(.white)
        .cornerRadius(10)
    }
    
}


struct KKLoadingView: View {
    var isNoMoreData = false
    
    var body: some View {
        HStack(alignment: .center, spacing: 10) {
            Spacer()
            if isNoMoreData {
                Text("No more data.")
                    .font(.title2)
                    .foregroundColor(Color.rgba(145, 144, 148))
            } else {
                ProgressView()
                Text("Loading...")
                    .font(.title2)
                    .foregroundColor(Color.rgba(145, 144, 148))
            }
            
            Spacer()
        }
    }
}
