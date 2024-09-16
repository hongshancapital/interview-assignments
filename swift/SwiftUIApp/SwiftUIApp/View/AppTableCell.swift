//
//  AppTableCell.swift
//  SwiftUIApp
//
//  Created by quanhai on 2022/6/28.
//

import SwiftUI

struct AppTableCell: View {
	@ObservedObject private var app: AppModel = AppModel()
	@State private var scaleFactor: CGFloat = 1
	@EnvironmentObject private var viewModel: AppViewModel
	
	init(appModel: AppModel){
		self.app = appModel
	}
	
	var body: some View{
		HStack(alignment: .center){
			// app icon
			AsyncImage(url: URL(string: app.appIcon)) {
				$0.resizable()
			} placeholder: {
				ProgressView()
			}
				.frame(width: 48, height: 48, alignment: .center)
				.cornerRadius(8)
				.overlay{
					RoundedRectangle(cornerRadius: 8, style: .continuous)
						.stroke(Color("appIconBorderColor"), lineWidth: 1)
				}
			// app name description
			VStack(alignment: .leading){
				Text(app.appName)
					.font(.headline)
					.lineLimit(1)
				Text(app.appDes)
					.font(.system(size: 12))
					.lineLimit(2)
			}
			Spacer()
			// TODO: -  进一步了解， button action区域响应异常
			// like or unlike , why button response area in whole cell row ? so use image instead
			Image(systemName: AppModel.likeIconName(app.like))
				.frame(width: 40, height: 40)
				.foregroundColor(AppModel.likeIconColor(app.like))
				.scaleEffect(AppModel.likeIconScale(app.like))
				.animation(.default, value: scaleFactor)
				.onTapGesture {
					Task {
						await changeLike()
					}
				}
		}
			.padding(EdgeInsets(top: 12, leading: 16, bottom: 12, trailing: 8))
			.background(.white)
			.cornerRadius(10)
	}

	private func changeLike() async{
		let islike = !app.like
		scaleFactor = AppModel.likeIconScale(islike)
		//
		await viewModel.updateAppLike(app.id, isLike: islike)
	}
}

struct AppTableCell_Previews: PreviewProvider {
    static var previews: some View {
		AppTableCell(appModel: AppModel())
    }
}
