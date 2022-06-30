//
//  ContentView.swift
//  App
//
//  Created by xiongjin on 2022/6/29.
//

import SwiftUI
import Foundation
import Combine
import Kingfisher

struct ContentView: View {
    
    @ObservedObject var viewModel = ResourceViewModel()
    @State var selections: [String] = [];
    
    var bColor: Color = Color(hex:0xf4f4f7)
    
    init() {
        UITableView.appearance().backgroundColor = UIColor(bColor)
    }
        
    var body: some View {
        NavigationView {
            List {
                ForEach(viewModel.resourceList) { ResponseResult in
                    
                    RowContent(obj: ResponseResult, isFavorites: self.selections.contains(ResponseResult.bundleId), pubColor: bColor) {
                        
                        checkfavoritesEvent(bundleId: ResponseResult.bundleId)
                    }
                }
            }
            .navigationBarTitle("App")
        }
        .navigationBarColor(backgroundColor: UIColor(bColor), tintColor: .black)
    }
    
    func checkfavoritesEvent(bundleId: String) {
        if self.selections.contains(bundleId) {
            self.selections.removeAll(where: {$0 == bundleId})
        } else {
            self.selections.append(bundleId)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

struct RowContent: View {
    
    var obj: ResponseResult
    var isFavorites: Bool
    var pubColor: Color
    var favoritesAction: () -> Void
    
    var url: URL {
        URL(string: obj.artworkUrl512)!
    }
    
    @State private var remoteImage : UIImage? = nil
    var body: some View {
        HStack {
            HStack {
                HStack {
                    Spacer()
                    KFImage.url(url)
                        .resizable()
                        .placeholder {
                            ProgressView()
                                .padding(.trailing, 20)
                                .progressViewStyle(
                                    CircularProgressViewStyle(tint: Color.gray)
                                )
                        }
                        .fade(duration: 1)
                        .aspectRatio(contentMode: .fit)
                        .cornerRadius(10)
                        .frame(width: 70, height: 70)
                }
                .frame(width: 80)
                VStack(alignment: .leading) {
                    Text(obj.trackCensoredName)
                        .lineLimit(1)
                        .font(
                            .system(size: 20)
                        )
                        .padding(.bottom, 2)
                    Text(obj.description)
                        .lineLimit(2)
                        .font(
                            .system(size: 16)
                        )
                }
                .padding(10)
                Spacer()
                Button(
                    action: self.favoritesAction
                ) {
                    Image(systemName: self.isFavorites ? "heart.fill" : "heart")
                        .resizable()
                        .foregroundColor(self.isFavorites ? .red : .gray)
                        .frame(width: 22, height: 20)
                }
                .frame(width: 40, height: 50)
                .buttonStyle(BorderlessButtonStyle())
                .padding(.trailing, 5)
            }
            .background(Color.white)
            .cornerRadius(10)
            .padding(EdgeInsets(top: 8, leading: 15, bottom: 8, trailing: 15))
        }
        .background(pubColor)
        .hideRowSeparator(background: pubColor)
    }
}




