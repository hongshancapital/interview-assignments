//
//  STSelectListView.swift
//  STToDoListDemo
//
//  Created by song on 2021/11/23.
//

import UIKit

protocol STSelectListViewDelegate: NSObject {
    func listViewBgBtnClick()
    func listViewNumberOfRowsInSection() -> Int
    func listViewCellForRowAt(row: Int) -> String
    func listViewDidSelectRowAt(row: Int, name: String)
}

class STSelectListView: UIView {
    
    weak var delegate: STSelectListViewDelegate?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.addSubview(self.bgBtn)
        self.addSubview(self.tableView)
        self.bgBtn.snp.makeConstraints { make in
            make.left.right.bottom.top.equalToSuperview()
        }
        self.tableView.snp.makeConstraints { make in
            make.left.equalTo(20)
            make.right.equalTo(-20)
            make.height.equalTo(150)
            make.centerY.equalTo(self.snp.centerY)
        }
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func reloadData() {
        self.tableView.reloadData()
    }
    
    @objc func bgBtnClick() {
        self.delegate?.listViewBgBtnClick()
    }
    
    private lazy var tableView: UITableView = {
        var view = UITableView()
        view.delegate = self
        view.dataSource = self
        view.layer.cornerRadius = 10
        view.separatorStyle = .none
        view.tableFooterView = UIView()
        view.backgroundColor = UIColor.white
        view.tableFooterView = UIView()
        return view
    }()
    
    private lazy var bgBtn: UIButton = {
        var view = UIButton()
        view.backgroundColor = UIColor.black
        view.addTarget(self, action: #selector(bgBtnClick), for: .touchUpInside)
        return view
    }()
}

extension STSelectListView: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.delegate?.listViewNumberOfRowsInSection() ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell = tableView.dequeueReusableCell(withIdentifier: "STSelectListView")
        if cell == nil {
            cell = UITableViewCell.init(style: .default, reuseIdentifier: "STSelectListView")
            cell?.selectionStyle = .none
            cell?.backgroundColor = UIColor.white
            cell?.textLabel?.textColor = UIColor.black
        }
        cell?.textLabel?.text = self.delegate?.listViewCellForRowAt(row: indexPath.row)
        return cell ?? UITableViewCell()
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if let name = self.delegate?.listViewCellForRowAt(row: indexPath.row) {
            self.delegate?.listViewDidSelectRowAt(row: indexPath.row, name: name)
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 40
    }
}
