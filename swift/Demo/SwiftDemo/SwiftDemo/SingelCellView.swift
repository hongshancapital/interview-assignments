//
//  SingelCellView.swift
//  SwiftDemo
//
//  Created by 彭军涛 on 2022/7/10.
//

import SwiftUI

struct SingleCellView: View {
    @EnvironmentObject var model: DemoViewModel
    @State var data: DemoModel.ScreenshotUrls
    var body: some View {
        VStack() {
            HStack() {
                AsyncImage(url: URL(string: data.artworkUrl60 ?? "")) { image in
                    image
                        .resizable()
                        .frame(width: 50, height: 50)
                        .cornerRadius(10)
                        .padding(.init(top: 10, leading: 10, bottom: 10, trailing: 0))
                } placeholder: {
                    ProgressView()
                        .frame(width: 50, height: 50)
                    .cornerRadius(10)
                    .padding(.init(top: 10, leading: 10, bottom: 10, trailing: 0))
                }

                VStack(alignment: .leading, spacing: 5) {
                    Text(data.trackCensoredName ?? "")
                        .font(.system(size: 18, weight: .bold, design: .default))
                        .lineLimit(1)
                    Text(data.description ?? "")
                        .font(.system(size: 12, weight: .regular, design: .default))
                        .lineLimit(2)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.init(top: 10, leading: 0, bottom: 10, trailing: 0))

                Button {
                    print("button")
                    model.setListData(bundleId: data.bundleId)
                } label: {
                    Image(systemName: model.dic[data.bundleId] != nil ? "heart.fill" : "heart")
                        .resizable()
                        .frame(width: 20, height: 20)
                        .tint(model.dic[data.bundleId] != nil ? Color.red : Color.black)
                        .scaleEffect(model.dic[data.bundleId] != nil ? 1.2 : 1, anchor: .center)
                }
                .padding(.init(top: 0, leading: 5, bottom: 0, trailing: 15))
            }
            .background(Color.white)
            .cornerRadius(10)
            .padding(.bottom, 10)

        }.listRowBackground(Color(.displayP3, red: 242 / 255, green: 242 / 255, blue: 247 / 255, opacity: 1))
            .listRowSeparator(.hidden)
    }
}

struct SingleCellView_Previews: PreviewProvider {
    static var previews: some View {
        SingleCellView(data: DemoModel.ScreenshotUrls(trackCensoredName: "hh", description: "jjj", artworkUrl60: "jj", bundleId: "io"))
    }
}
