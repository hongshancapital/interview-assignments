//
//  IAHomeCellView.swift
//  InterviewAssignments
//
//  Created by ZhuChaoJun on 2022/6/2.
//

import SwiftUI

struct IAHomeCellView: View {
    @ObservedObject var viewModel: IAHomeCellViewModel

    var body: some View {
        HStack {
            // image
            AsyncImage(url: viewModel.model.url) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fit)
            } placeholder: {
                ProgressView().progressViewStyle(.circular)
            }
            .frame(width: 60, height: 60)
            .cornerRadius(10)

            // info
            VStack(alignment: .leading) {
                Text(viewModel.model.trackCensoredName)
                    .font(.system(size: 16))
                    .lineLimit(1)
                    .padding(.bottom, 3)
                Text(viewModel.model.description)
                    .lineLimit(2)
                    .font(.system(size: 12))
            }.padding(.trailing, 4)

            Spacer()

            // favorite
            Image(systemName: viewModel.model.isFavorite ? "heart.fill" : "heart")
                .foregroundColor(viewModel.model.isFavorite ? .red : .gray)
                .frame(width: 20, height: 30)

        }
        .padding()
        .background(.white, in: RoundedRectangle(cornerRadius: 10))
        .listRowSeparator(.hidden)
        .listRowBackground(Color.clear)
        .onTapGesture(perform: self.viewModel.toggleFavorite)

    }
}

struct IAHomeCellView_Previews: PreviewProvider {
    static var previews: some View {
        let appInfo = IAAppInfoModel(trackCensoredName: "名字", artworkUrl60: "", description: "")
        IAHomeCellView(viewModel: IAHomeCellViewModel(appInfo))
    }
}
