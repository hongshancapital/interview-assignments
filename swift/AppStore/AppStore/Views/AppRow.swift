//
//  AppRow.swift
//  AppList
//
//  Created by Ma on 2022/3/13.
//

import SwiftUI

struct AppRow: View {
    @ObservedObject var viewModel : AppItemViewModel

    var body: some View {
        GeometryReader { _ in
            HStack(alignment: .center) {
                
                AsyncImage(url: URL.init(string: viewModel.app.artworkUr)) { image in
                    image.resizable()
                } placeholder: {
                    ProgressView()
                }
                .frame(width: 60, height: 60, alignment: .center)
                .cornerRadius(6)
                .padding(10)
                
                VStack(alignment: .leading, content: {
                    Text(viewModel.app.title)
                        .font(Font.system(size: 16))
                        .lineLimit(1)
                        .padding(EdgeInsets.init(top: 10, leading: 6, bottom: 4, trailing: 6))
                    Text(viewModel.app.description)
                        .font(Font.system(size: 12))
                        .foregroundColor(Color.gray)
                        .lineLimit(2)
                        .padding(EdgeInsets.init(top: 0, leading: 6, bottom: 4, trailing: 6))
                })
                Spacer()
                Image(viewModel.app.isFavorated ? "liked_icon" : "unliked_icon")
                    .frame(width: 25)
                    .onTapGesture {
                        if viewModel.app.isFavorated {
                            viewModel.excute(.unlike)
                        }
                        else {
                            viewModel.excute(.like)
                        }
                    }
            }
            .frame(height: 80)
            .padding(10)
            .background(Color.white)
            .cornerRadius(6)
        }
        .frame(height: 120)
        .listRowBackground(Color.clear)
    }
}

//struct AppRow_Previews: PreviewProvider {
//    static var previews: some View {
//        AppRow(row: <#Int#>)
//    }
//}
