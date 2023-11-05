class Bundlr {

    constructor(formElement, projectName,bundleButton,bomButton) {
        this.projectName = projectName;
        this.formField = document.getElementById(formElement);
        this.bundleButton = document.getElementById(bundleButton);
        this.bomButton = document.getElementById(bomButton);

        this.bundleButton.onclick = this.generateZipBundle.bind(this);
        this.bomButton.onclick = this.generateBOM.bind(this);
    }
    generateZipBundle() {
        this.formField.action = "/generate/bundle/"+this.projectName;
        this.formField.submit();
    }
    generateBOM() {
        this.formField.action = "/generate/bom/"+this.projectName;
        this.formField.submit();
    }

    init() {
        const formField = this.formField
        fetch('/generate/forminfo/test')
            .then(value => value.json())
            .then(jsonData => {
                console.log(jsonData);
                for (const component of jsonData.components.slice().reverse()) {
                    const selectList = document.createElement("select");
                    selectList.name = component.id;
                    formField.prepend(selectList);

                    const titleOption = document.createElement("option");
                    titleOption.text = component.name;
                    titleOption.disabled = true;
                    titleOption.selected = true;
                    selectList.appendChild(titleOption);

                    for (const opt of component.options) {
                        const option = document.createElement("option");
                        option.value = opt.id;
                        option.text = opt.value;
                        selectList.appendChild(option);
                    }
                }
            });
        return this;
    }
}