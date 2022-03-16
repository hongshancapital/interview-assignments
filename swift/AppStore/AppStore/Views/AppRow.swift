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
                    image
                        .resizable()
                        .overlay(RoundedRectangle(cornerRadius: 8, style: .continuous)
                                    .stroke(.gray, lineWidth: 0.5)
                            )
                } placeholder: {
                    ProgressView()
                }
                .frame(width: 50, height: 50, alignment: .center)
                .scaledToFit()
                .cornerRadius(8)
                
                
                VStack(alignment: .leading,spacing: 4, content: {
                    Text(viewModel.app.title.trimmingCharacters(in: CharacterSet.whitespacesAndNewlines))
                        .font(Font.system(size: 15))
                        .lineLimit(1)
                        
                    Text(viewModel.app.description.trimmingCharacters(in: .whitespacesAndNewlines).replacingOccurrences(of: "\n", with: ""))
                        .font(Font.system(size: 12))
                        .foregroundColor(Color.gray)
                        .lineLimit(2)
//                        .padding(.top,0.5)
//                        .padding(EdgeInsets.init(top: 0, leading: 6, bottom: 4, trailing: 6))
                })
                Spacer()
                Image(viewModel.app.isFavorated ? "liked_icon" : "unliked_icon")
                    .frame(width: 15,height: 15)
                    .padding(10)
                    .scaleEffect(viewModel.app.isFavorated ? 0.7 : 0.6)
                    .scaledToFit()
                    .animation(.default, value: viewModel.app.isFavorated)
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
            .padding(.horizontal, 10)
            .background(Color.white)
            .cornerRadius(10)
        }
        .frame(height: 80)
        .listRowBackground(Color.clear)
    }
}

//struct AppRow_Previews: PreviewProvider {
//    static var previews: some View {
//        AppRow(row: <#Int#>)
//    }
//}
