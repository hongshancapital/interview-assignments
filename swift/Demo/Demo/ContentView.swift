//
//  ContentView.swift
//  Demo
//
//  Created by Kai on 2022/2/16.
//

import SwiftUI

struct ContentView: View {
    
    @EnvironmentObject var store: Store
    @State private var page = 0
    
    private func loadData() {
        self.store.dispatch(.loadPageList(page: page))
    }
    
    var body: some View {
 
        NavigationView {
            if store.appState.appPageList.count == 0 {
                ProgressView()
                    .navigationTitle("App")
                    .onAppear {
                        self.store.dispatch(.loadAllList)
                    }
            } else {
                List {
                    ForEach(self.store.appState.appPageList) { model in
                        KKCell(model: model, store: store)
                            .cornerRadius(10)
                            .listRowSeparator(.hidden)
                            .listRowBackground(Color.clear)
                    }
                    KKLoadingView(isNoMoreData: $store.appState.isNoMoreData)
                        .listRowBackground(Color.clear)
                        .onAppear {
                            page += 1
                            loadData()
                        }
                }
                .listStyle(.grouped)
                .navigationBarTitle("App")
                .refreshable {
                    page = 0
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
    var store: Store
    var isSelect: Bool = false

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
                withAnimation {
                    self.store.dispatch(.changeLikeState(model: model))
                }
                
            } label: {
                let imgName = model.like ?? false ? "suit.heart.fill" : "suit.heart"
                let color = model.like ?? false ? UIColor(235, 88, 71) : UIColor(153, 153, 153)
                Image(uiImage: UIImage(systemName: imgName)!.xk_imageWithColor(color: color))
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 20, height: 20)
                    .scaleEffect(model.like ?? false ? 1.1 : 1)
            }
            .buttonStyle(BorderlessButtonStyle())
        }
        .padding(10)
        .background(.white)
        .cornerRadius(10)
    }

}


struct KKLoadingView: View {
    @Binding var isNoMoreData: Bool

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
